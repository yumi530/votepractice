package com.project.voting.controller.exception;

import com.project.voting.exception.admin.AdminCustomException;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.candidate.CandidateCustomException;
import com.project.voting.exception.election.ElectionCustomException;
import com.project.voting.exception.users.UsersCustomException;
import com.project.voting.exception.vote_box.VoteBoxCustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler
  public String adminException(AdminCustomException adminException, Model model) {
    model.addAttribute("exception", adminException);
    return "/error/admin";
  }

  @ExceptionHandler
  public String electionException(ElectionCustomException electionException, Model model) {
    model.addAttribute("exception", electionException);
    return "/error/election";
  }

  @ExceptionHandler
  public String usersException(UsersCustomException usersException, Model model) {
    model.addAttribute("exception", usersException);
    return "/error/users";
  }

  @ExceptionHandler
  public String candidateException(CandidateCustomException candidateException, Model model) {
    model.addAttribute("exception", candidateException);
    return "/error/candidate";
  }

  @ExceptionHandler
  public String candCountException(CandCountCustomException candCountException, Model model) {
    model.addAttribute("exception", candCountException);
    return "/error/cand_count";
  }

  @ExceptionHandler
  public String voteBoxException(VoteBoxCustomException voteBoxCustomException, Model model) {
    model.addAttribute("exception", voteBoxCustomException);
    return "/error/vote_box";
  }
}
