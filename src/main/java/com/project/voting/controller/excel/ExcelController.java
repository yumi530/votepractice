//package com.project.voting.controller.excel;
//
//import com.project.voting.dto.users.BasicResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.multipart.MultipartFile;
//
//@Controller
//@RequiredArgsConstructor
//public class ExcelController {
//
//  @PostMapping(value = "/addExcel")
//  public ResponseEntity<? extends BasicResponse> addExcel(HttpServletRequest request,
//    HttpServletResponse response, MultipartFile file) {
//
//    return ResponseEntity.ok().body(electionService.insertExcel(file));
//  };
//
//}
