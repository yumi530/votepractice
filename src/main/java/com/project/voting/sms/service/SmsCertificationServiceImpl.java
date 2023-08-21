//package com.project.voting.sms.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.voting.config.RedisService;
//import com.project.voting.sms.dto.*;
//import lombok.RequiredArgsConstructor;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import javax.transaction.Transactional;
//import java.io.UnsupportedEncodingException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
//@PropertySource("classpath:application.yml")
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class SmsCertificationServiceImpl implements SmsCertificationService {
//    private final String verificationCode = createSmsKey();
//    private final StringRedisTemplate redisTemplate;
//    private final RedisService redisService;
//
//
//    @Value("${naver-cloud-sms.accessKey}")
//    private String accessKey;
//
//    @Value("${naver-cloud-sms.secretKey}")
//    private String secretKey;
//
//    @Value("${naver-cloud-sms.serviceId}")
//    private String serviceId;
//
//    @Value("${naver-cloud-sms.senderPhone}")
//    private String senderNumber;
//
//    @Override
//    public String makeSignature(String time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
//        String space = " ";
//        String newLine = "\n";
//        String method = "POST";
//        String url = "/sms/v2/services/" + this.serviceId + "/messages";
//        String accessKey = this.accessKey;
//        String secretKey = this.secretKey;
//
//
//        String message = new StringBuilder()
//                .append(method)
//                .append(space)
//                .append(url)
//                .append(newLine)
//                .append(time)
//                .append(newLine)
//                .append(accessKey)
//                .toString();
//
//        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(signingKey);
//
//        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
//        String encodeBase64String = Base64.encodeBase64String(rawHmac);
//
//        return encodeBase64String;
//    }
//
//
//    @Override
//    public NaverSmsResponseDTO sendVerificationMessage(NaverSmsMessageDTO naverSmsMessageDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, URISyntaxException {
//
//        String time = Long.toString(System.currentTimeMillis());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("x-ncp-apigw-timestamp", time);
//        headers.set("x-ncp-iam-access-key", accessKey);
//        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));
//
//        List<NaverSmsMessageDTO> messages = new ArrayList<>();
//        messages.add(naverSmsMessageDTO);
//
//        NaverSmsRequestDTO request = NaverSmsRequestDTO.builder()
//                .type("SMS")
//                .contentType("COMM")
//                .countryCode("82")
//                .from(senderNumber)
//                .content("인증번호 [" + verificationCode + "]를 입력해주세요")
//                .messages(messages)
//                .build();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String body = objectMapper.writeValueAsString(request);
//
//        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//
//        NaverSmsResponseDTO naverSmsResponseDTO = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), httpBody, NaverSmsResponseDTO.class);
//        NaverSmsResponseDTO responseDto = new NaverSmsResponseDTO(verificationCode);
//
//        redisTemplate.opsForValue().set(naverSmsMessageDTO.getTo(), verificationCode, 3, TimeUnit.MINUTES);
//        return naverSmsResponseDTO;
//    }
//
//    public static String createSmsKey() {
//        StringBuffer key = new StringBuffer();
//        Random rnd = new Random();
//
//        for (int i = 0; i < 7; i++) {
//            key.append((rnd.nextInt(10)));
//        }
//        return key.toString();
//    }
//
//    @Override
//    public String verifyCode(SmsCertificationRequestDTO smsCertificationRequestDto) {
//        String phoneNumber = smsCertificationRequestDto.getPhoneNumber();
//        String code = smsCertificationRequestDto.getCode();
//        String key = phoneNumber;
//
//        //redis 에 해당 번호의 키가 없는 경우는 인증번호(3분) 만료로 처리
//        if (!redisService.hasKey(key)) {
//            throw new RuntimeException("ErrorCode. EXPIRED_VERIFICATION_CODE");
//        }
//
//        //redis 에 해당 번호의 키와 인증번호가 일치하지 않는 경우
//        if (!redisService.getValues(key).equals(code)) {
//            throw new RuntimeException("ErrorCode.MISMATCH_VERIFICATION_CODE");
//        }
//
//        //필터를 모두 통과, 인증이 완료되었으니 redis 에서 전화번호 삭제
//        redisService.deleteValues(key);
//        return "인증에 성공하였습니다";
//    }
//}
