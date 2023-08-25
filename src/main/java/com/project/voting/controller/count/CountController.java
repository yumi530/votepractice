package com.project.voting.controller.count;


import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.count.CountDto;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.dto.users.UsersDto;
import com.project.voting.service.count.CountService;
import com.project.voting.service.election.ElectionService;
import com.project.voting.service.users.UsersService;
import com.project.voting.service.vote.VoteService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner.Mode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class CountController {

  private final ElectionService electionService;
  private final UsersService usersService;
  private final CountService countService;
  private final VoteService voteService;

  @GetMapping("/count/list")
  public String countList(@AuthenticationPrincipal @RequestParam(name = "phoneNumber") String usersPhone, Model model){
    List<UsersDto> usersDtoList = usersService.detailList(usersPhone);
    model.addAttribute("usersDtoList", usersDtoList);
    return "users/count/list";
  }
  @GetMapping("/count/detail/{electionId}")
  public String countDetail(Model model, @PathVariable Long electionId) {
    Election detail = electionService.detail(electionId);
    model.addAttribute("detail", detail);

    LocalDateTime currentDate = LocalDateTime.now();
    LocalDateTime endDate = LocalDateTime.parse(detail.getElectionEndDt());
    boolean isBefore = currentDate.isBefore(endDate);
    model.addAttribute("isBefore", isBefore);

    return "users/count/detail";
  }
  @GetMapping("/count/voteCount/{voteId}")
  public String voteCount(Model model, @PathVariable(name = "voteId") Long voteId){
    Vote detail = voteService.detail(voteId);
    model.addAttribute("detailVote", detail);
    return "users/count/vote-count";
  }
  @PostMapping("/save")
  public String countSave(@RequestParam Long voteId, @RequestParam boolean isAgreed, HttpSession session, Model model) {
    if (countService.hadVoted(session, voteId)) {
      throw new RuntimeException("이미 투표를 완료하였습니다.");
    }
    Count save = countService.save(isAgreed, voteId);
    countService.confirmVoted(session, voteId);
    model.addAttribute("save", save);
    return "users/count/vote-complete";
  }

  @GetMapping("/count/voteResult/{voteId}")
  public String voteResult(Model model, @PathVariable Long voteId){
    Vote voteResult = countService.countVotesResultConfirm(voteId);
    model.addAttribute("voteResult", voteResult);
    return "users/count/vote-result";
  }

//  @PostMapping("/count/voteCount/complete")
//  public String electionComplete(String usersPhone, boolean usersCompleted) {
//    usersService.complete(usersPhone, usersCompleted);
//    return "index";
//  }
}


