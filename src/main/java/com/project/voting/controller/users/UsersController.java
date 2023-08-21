package com.project.voting.controller.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.voting.dto.users.UsersDto;
import com.project.voting.service.users.UsersService;
import com.project.voting.sms.dto.*;
import com.project.voting.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.apache.tika.metadata.DublinCore.CREATED;

@Controller
@RequiredArgsConstructor
public class UsersController {
    private final SmsService smsService;

//    private final SmsCertificationService smsCertificationService;
    private final StringRedisTemplate redisTemplate;

    @GetMapping("/users/login")
    public String getSmsPage() {
        return "users/login";
    }

    @PostMapping("/users/logins")
    public String sendSms(MessageDto messageDto, Model model) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        smsService.sendSms(messageDto.getTo());
        model.addAttribute("message", messageDto);
        return "users/phone-auth";
    }


//    @PostMapping("/users/logins")
//    public String sendMessageWithVerificationCode(NaverSmsMessageDTO messageDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, URISyntaxException {
//        smsCertificationService.sendVerificationMessage(messageDto);
//        return "users/phone-auth";
//    }



//        @PostMapping("/send")
//        public CommonApiResponse<String> sendMessageWithVerificationCode(@RequestBody @Valid SmsVerificationCodeRequestDTO dto) {
//            String result = smsCertificationService.sendVerificationMessage(dto.getPhoneNumber());
//            return CommonApiResponse.of(result);
//        }

        @PostMapping("/users/verify")
        public String verifyCode(String phoneNumber, String code) {
            smsService.verifyCode(phoneNumber, code);
            return "index";
        }
    }

//        //인증번호 발송
//        @PostMapping("/sms-certification/sends")
//        public ResponseEntity<Void> sendSms(@RequestBody SmsCertificationRequest requestDto) {
//            smsCertificationService.sendSms(requestDto.getPhone());
//            return CREATED;
//        }
//
//        //인증번호 확인
//        @PostMapping("/sms-certification/confirms")
//        public ResponseEntity<Void> SmsVerification(@RequestBody SmsCertificationRequest requestDto) {
//            smsCertificationService.verifySms(requestDto);
//            return OK;
//        }
//    }

