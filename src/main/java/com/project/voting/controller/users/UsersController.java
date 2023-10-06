package com.project.voting.controller.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.voting.dto.sms.MessageDto;
import com.project.voting.service.sms.SmsService;
import com.project.voting.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final SmsService smsService;

    public class SessionConst {
        public static final String LOGIN_USER = "loginUser";
    }

    @GetMapping("/login")
    public String getSmsPage() {
        return "users/login";
    }

    @PostMapping("/logins")
    public String sendSms(MessageDto messageDto, Model model)
            throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        smsService.sendSms(messageDto.getTo());
        model.addAttribute("message", messageDto);
        return "users/phone-auth";
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam(required = false) String phoneNumber,
                             @RequestParam("code") String code, Model model, HttpServletRequest request) {
        smsService.verifyCode(phoneNumber, code);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, phoneNumber);
        model.addAttribute("phoneNumber", phoneNumber);
        return "index";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, String usersPhone) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
