package com.project.voting.controller.error;

import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class CustomErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {

    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));

    if (status != null) {

      int statusCode = Integer.valueOf(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {

        model.addAttribute("code", status.toString());
        model.addAttribute("message", httpStatus.getReasonPhrase());
        model.addAttribute("timestamp", new Date());
        return "/error/404";
      }
      if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        return "/error/500";
      }
    }
    return "/error/error";
  }
}
