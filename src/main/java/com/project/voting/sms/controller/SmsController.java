package com.project.voting.sms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.project.voting.sms.dto.SmsRequestDto;
//import com.project.voting.sms.dto.SmsResponseDto;
//import com.project.voting.sms.model.Request;
//import com.project.voting.sms.service.SmsService;
import com.project.voting.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

//    @GetMapping("/users/login")
//    public String getSmsPage() {
//        return "users/login";
//    }
//
//    @PostMapping("/users/login")
//    public String phoneAuth(String tel, Model model) {
//
//        smsService.sendRandomMessage(tel);
//        model.addAttribute("tel", tel);
//
//        return "users/phone-auth";
//    }


//    @PostMapping("/users/sms/send")
//    public String sendSms(Request request, Model model) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
//        SmsResponseDto response = smsService.sendSms(request.getRecipientPhoneNumber(), request.getContent());
//        model.addAttribute("response", response);
//        return "users/sendSms";
//    }
}
