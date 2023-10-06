package com.project.voting.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
  @RequestMapping("/login")
  public String adminLogin(){

    return "admin/login";
  }

  @RequestMapping("/index")
  public String adminIndex() {

    return "admin/index";
  }
}
