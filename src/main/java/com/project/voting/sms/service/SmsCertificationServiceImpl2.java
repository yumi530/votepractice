//package com.project.voting.sms.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.voting.config.RedisService;
//import com.project.voting.sms.dto.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import static com.project.voting.sms.service.SmsService.createSmsKey;
//
//@PropertySource("classpath:application.yml")
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class SmsCertificationServiceImpl implements SmsCertificationService {
//
//    private final WebClient webClient;
//    private final RedisService redisService;
//
//    private final String VERIFICATION_PREFIX = "sms:";
//    private final int VERIFICATION_TIME_LIMIT = 3 * 60;
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
//    /**
//     * sms 전송을 위한 서명을 추가한다
//     *
//     * @param currentTime 현재 시간
//     * @return 서명
//     */
//    @Override
//    public String makeSignature(Long currentTime) {
//        String space = " ";
//        String newLine = "\n";
//        String method = "POST";
//        String url = "/sms/v2/services/" + this.serviceId + "/messages";
//        String timestamp = currentTime.toString();
//        String accessKey = this.accessKey;
//        String secretKey = this.secretKey;
//
//        try {
//
//            String message = method +
//                    space +
//                    url +
//                    newLine +
//                    timestamp +
//                    newLine +
//                    accessKey;
//
//            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
//            Mac mac = Mac.getInstance("HmacSHA256");
//            mac.init(signingKey);
//
//            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
//            String encodeBase64String = Base64.encodeBase64String(rawHmac);
//
//            return encodeBase64String;
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//    @Override
//    public String sendVerificationMessage(String to) {
//        final String smsURL ="https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages";
//        final Long time = System.currentTimeMillis();
//        //랜덤 6자리 인증번호
//        final String verificationCode = generateVerificationCode();
//        //3분 제한시간
//        final Duration verificationTimeLimit = Duration.ofSeconds(VERIFICATION_TIME_LIMIT);
//
//        try {
//            //네이버 sms 메세지 dto
//            final NaverSmsMessageDTO naverSmsMessageDTO = new NaverSmsMessageDTO(to, generateMessageWithCode(verificationCode));
//            List<NaverSmsMessageDTO> messages = new ArrayList<>();
//            messages.add(naverSmsMessageDTO);
//            final NaverSmsRequestDTO naverSmsRequestDTO = NaverSmsRequestDTO.builder()
//                    .type("SMS")
//                    .contentType("COMM")
//                    .countryCode("82")
//                    .from(senderNumber)
//                    .content(naverSmsMessageDTO.getContent())
//                    .messages(messages)
//                    .build();
//            final String body = new ObjectMapper().writeValueAsString(naverSmsRequestDTO);
//
////            final ResponseMessageDTO responseMessageDTO =
//            webClient.post().uri(smsURL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .header("x-ncp-apigw-timestamp", time.toString())
//                    .header("x-ncp-iam-access-key", accessKey)
//                    .header("x-ncp-apigw-signature-v2", makeSignature(time))
//                    .accept(MediaType.APPLICATION_JSON)
//                    .body(BodyInserters.fromValue(body))
//                    .retrieve()
//                    .bodyToMono(NaverSmsResponseDTO.class).block();
//
//            //redis 에 3분 제한의 인증번호 토큰 저장
//            redisService.setValues(VERIFICATION_PREFIX + to, verificationCode, verificationTimeLimit);
//
//            return "메세지 전송 성공";
////            return responseMessageDTO;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("메세지 발송에 실패하였습니다");
//        }
//    }
//    @Override
//    public NaverSmsResponseDTO sendVerificationMessage(String to)  {
//        final String verificationCode = createSmsKey();
//        String smsURL ="https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages";
//        final Long time = System.currentTimeMillis();
//        //랜덤 6자리 인증번호
//        final String verificationCode = generateVerificationCode();
//        //3분 제한시간
//        final Duration verificationTimeLimit = Duration.ofSeconds(VERIFICATION_TIME_LIMIT);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("x-ncp-apigw-timestamp", String.valueOf(time));
//        headers.set("x-ncp-iam-access-key", accessKey);
//        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));
//
//            //네이버 sms 메세지 dto
//            NaverSmsMessageDTO naverSmsMessageDTO = new NaverSmsMessageDTO(to, generateMessageWithCode(verificationCode));
//            List<NaverSmsMessageDTO> messages = new ArrayList<>();
//            messages.add(naverSmsMessageDTO);
//
//            NaverSmsRequestDTO naverSmsRequestDTO = NaverSmsRequestDTO.builder()
//                    .type("SMS")
//                    .contentType("COMM")
//                    .countryCode("82")
//                    .from(senderNumber)
//                    .content(naverSmsMessageDTO.getContent())
//                    .messages(messages)
//                    .build();
//
//            final String body = new ObjectMapper().writeValueAsString(naverSmsRequestDTO);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String body = objectMapper.writeValueAsString(naverSmsRequestDTO);
//
//            webClient.post().uri(smsURL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .header("x-ncp-apigw-timestamp", time.toString())
//                    .header("x-ncp-iam-access-key", accessKey)
//                    .header("x-ncp-apigw-signature-v2", makeSignature(time))
//                    .accept(MediaType.APPLICATION_JSON)
//                    .body(BodyInserters.fromValue(body))
//                    .retrieve()
//                    .bodyToMono(NaverSmsResponseDTO.class).block();
//        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//        NaverSmsResponseDTO smsResponseDto = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, NaverSmsResponseDTO.class);
//
//            //redis 에 3분 제한의 인증번호 토큰 저장
//            redisService.setValues(VERIFICATION_PREFIX + to, verificationCode, verificationTimeLimit);
//
//            return smsResponseDto;
//            return responseMessageDTO;
//    }
//
//    /**
//     * 랜덤 인증번호를 생성한다
//     * @return 인증번호 6자리
//     */
//    private String generateVerificationCode() {
//        Random random = new Random();
//        int verificationCode = random.nextInt(888888) + 111111;
//        return Integer.toString(verificationCode);
//    }
//
//    /**
//     * 인증번호가 포함된 메세지를 생성한다
//     * @param code 인증번호 6자리
//     * @return 인증번호 6자리가 포함된 메세지
//     */
//    private String generateMessageWithCode(String code) {
//        final String provider = "투표";
//        return "[" + provider + "] 인증번호 [" + code + "] 를 입력해주세요 :)";
//    }
//
//    @Override
//    public String verifyCode(SmsCertificationRequestDTO smsCertificationRequestDto) {
//        String phoneNumber = smsCertificationRequestDto.getPhoneNumber();
//        String code = smsCertificationRequestDto.getCode();
//        String key = VERIFICATION_PREFIX + phoneNumber;
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
