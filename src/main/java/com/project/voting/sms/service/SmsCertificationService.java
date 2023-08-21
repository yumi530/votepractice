//package com.project.voting.sms.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.project.voting.sms.dto.NaverSmsMessageDTO;
//import com.project.voting.sms.dto.NaverSmsResponseDTO;
//import com.project.voting.sms.dto.SmsCertificationRequestDTO;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URISyntaxException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//public interface SmsCertificationService {
//    String makeSignature(String time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException;
//
//    NaverSmsResponseDTO sendVerificationMessage(NaverSmsMessageDTO messageDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, URISyntaxException;
//
//    String verifyCode(SmsCertificationRequestDTO smsCertificationRequestDto);
//}
