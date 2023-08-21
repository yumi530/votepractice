package com.project.voting.sms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.voting.config.RedisService;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.sms.dto.MessageDto;
import com.project.voting.sms.dto.SmsCertificationRequestDTO;
import com.project.voting.sms.dto.SmsRequestDto;
import com.project.voting.sms.dto.SmsResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@PropertySource("classpath:application.yml")
@Service
@Transactional
@RequiredArgsConstructor
public class SmsService {
    private final UsersRepository usersRepository;
    private final String smsConfirmNum = createSmsKey();
    private final String VERIFICATION_PREFIX = "sms:";
    private final int VERIFICATION_TIME_LIMIT = 3 * 60;
    private final StringRedisTemplate redisTemplate;
    private final WebClient webClient;
    private final RedisService redisService;


    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    public String getSignature(String time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + this.serviceId + "/messages";
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;


        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(time)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }


    public String sendSms(String to) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        final Duration verificationTimeLimit = Duration.ofSeconds(VERIFICATION_TIME_LIMIT);
        Optional<Users> optionalUsers = usersRepository.findById(to);
        if (!optionalUsers.isPresent()) {
            throw new RuntimeException("선거인 명부에 존재하지 않습니다.");
        }
        String time = Long.toString(System.currentTimeMillis());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time);
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", getSignature(time));

        final MessageDto messageDto = new MessageDto(to, generateMessageWithCode(smsConfirmNum));
        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);

        SmsRequestDto request = SmsRequestDto.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content("인증번호 [" + smsConfirmNum + "]를 입력해주세요")
                .messages(messages)
                .build();
        final String body = new ObjectMapper().writeValueAsString(request);

//            final ResponseMessageDTO responseMessageDTO =
        webClient.post().uri("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-ncp-apigw-timestamp", time)
                .header("x-ncp-iam-access-key", accessKey)
                .header("x-ncp-apigw-signature-v2", getSignature(time))
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(SmsResponseDto.class).block();

//        ObjectMapper objectMapper = new ObjectMapper();
//        String body = objectMapper.writeValueAsString(request);
//
//        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//
//        SmsResponseDto smsResponseDto = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), httpBody, SmsResponseDto.class);
//        SmsResponseDto responseDto = new SmsResponseDto(smsConfirmNum);

//            redisTemplate.opsForValue().set(messageDto.getTo(), smsConfirmNum, 3, TimeUnit.MINUTES);
        redisService.setValues(VERIFICATION_PREFIX + messageDto.getTo(), smsConfirmNum, verificationTimeLimit);

        return "전송 성공";
    }

    public String verifyCode(String phoneNumber, String code) {
//        String phoneNumber = smsCertificationRequestDto.getPhoneNumber();
//        String code = smsCertificationRequestDto.getCode();
        String key = VERIFICATION_PREFIX + phoneNumber;

//        redisService.setValues(key, code);

//        redis 에 해당 번호의 키가 없는 경우는 인증번호(3분) 만료로 처리
//        if (!redisService.hasKey(key)) {
//            throw new RuntimeException("ErrorCode. EXPIRED_VERIFICATION_CODE");
//        }

        //redis 에 해당 번호의 키와 인증번호가 일치하지 않는 경우
//        if (!redisService.getValue(key).equals(code)) {
//            throw new RuntimeException("ErrorCode.MISMATCH_VERIFICATION_CODE");
//        }
        if(redisService.getValue(key).equals(code));

        //필터를 모두 통과, 인증이 완료되었으니 redis 에서 전화번호 삭제
        redisService.deleteValues(key);

        return "인증 성공";
    }

    public static String createSmsKey() {
        StringBuffer smsKey = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 7; i++) {
            smsKey.append((rnd.nextInt(10)));
        }
        return smsKey.toString();
    }
    private String generateMessageWithCode(String code) {
        final String provider = "투표";
        return "[" + provider + "] 인증번호 [" + code + "] 를 입력해주세요 :)";
    }
}