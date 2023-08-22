package com.project.voting.sms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.voting.config.RedisService;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.service.users.UsersService;
import com.project.voting.sms.dto.MessageDto;
import com.project.voting.sms.dto.SmsRequestDto;
import com.project.voting.sms.dto.SmsResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@PropertySource("classpath:application.yml")
@Service
@Transactional
@RequiredArgsConstructor
public class SmsService {
    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private final String smsConfirmNum = createSmsKey();
    private final String VERIFICATION_PREFIX = "sms:";
    private final int VERIFICATION_TIME_LIMIT = 3 * 60;

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

        webClient.post().uri("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-ncp-apigw-timestamp", time)
                .header("x-ncp-iam-access-key", accessKey)
                .header("x-ncp-apigw-signature-v2", getSignature(time))
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(SmsResponseDto.class).block();

        redisService.setValues(VERIFICATION_PREFIX + messageDto.getTo(), smsConfirmNum, verificationTimeLimit);

        return "전송 성공";
    }

    public String verifyCode(String phoneNumber, String code) {
        String key = VERIFICATION_PREFIX + phoneNumber;

        if (!redisService.hasKey(key)) {
            throw new RuntimeException("ErrorCode. EXPIRED_VERIFICATION_CODE");
        }

        if (!redisService.getValue(key).equals(code)) {
            throw new RuntimeException("ErrorCode.MISMATCH_VERIFICATION_CODE");
        }
        if(redisService.getValue(key).equals(code));

        Users users = usersRepository.findById(phoneNumber).orElse(null);
//        if (users != null) {
//
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
//            UserDetails userDetails = new User(users.getUsersPhone(), "", authorities);
//
//            // 사용자 엔티티에 UserDetails 설정
//            users.setUserDetails(userDetails);
//
//            usersRepository.save(users);
//        }

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
        return "[" + provider + "] 인증번호 [" + code + "] 를 입력해주세요.";
    }
}