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
  public String sendSms(MessageDto messageDto, Model model)
    throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
    smsService.sendSms(messageDto.getTo());
    model.addAttribute("message", messageDto);
    return "users/phone-auth";
  }

  @PostMapping("/users/verify")
  public String verifyCode(@RequestParam(required = false) String phoneNumber,
    @RequestParam("code") String code) {
    smsService.verifyCode(phoneNumber, code);
    return "index";
  }
}
