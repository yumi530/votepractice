package com.project.voting.controller.count;


import ch.qos.logback.core.joran.conditional.IfAction;
import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.users.UsersDto;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.service.candidate.CandidateService;
import com.project.voting.service.count.CountService;
import com.project.voting.service.election.ElectionService;
import com.project.voting.service.users.UsersService;
import com.project.voting.service.vote.VoteService;
import com.project.voting.service.voteBox.VoteBoxService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner.Mode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class CountController {

  private final ElectionService electionService;
  private final UsersService usersService;
  private final CountService countService;
  private final VoteService voteService;
  private final CandidateService candidateService;
  private final VoteBoxService voteBoxService;

  @GetMapping("/count/list")
  public String list(@AuthenticationPrincipal @RequestParam(name = "phoneNumber") String usersPhone,
    Model model) {
    List<UsersDto> usersDtoList = usersService.detailList(usersPhone);
    model.addAttribute("usersDtoList", usersDtoList);
    return "users/count/list";
  }

  @GetMapping("/count/detail/{electionId}")
  public String detail(Model model, @PathVariable Long electionId, @RequestParam String usersPhone) {
    Election detail = electionService.detail(electionId);
    model.addAttribute("detail", detail);
    model.addAttribute("usersPhone", usersPhone);

    LocalDateTime currentDate = LocalDateTime.now();
    LocalDateTime endDate = detail.getElectionEndDt();
    boolean isBefore = currentDate.isBefore(endDate);
    model.addAttribute("isBefore", isBefore);

    return "users/count/detail";
  }

  @GetMapping("/count/voteCount/{voteId}")
  public String voteCount(Model model, @PathVariable(name = "voteId") Long voteId, @RequestParam(name = "usersPhone") String usersPhone) {
    Election detailElection = electionService.detailElection(voteId);
    Vote detailVote = voteService.detail(voteId);
    List<Candidate> detailCand = candidateService.detail(voteId);
    List<VoteBox> detailVoteBox = voteBoxService.detailVoteBox(voteId);

    VoteBoxDto voteBoxDto = new VoteBoxDto();
    voteBoxDto.setDetailVoteBox(detailVoteBox);

    model.addAttribute("detailCand", detailCand);
    model.addAttribute("detailVote", detailVote);
    model.addAttribute("detailElection", detailElection);
    model.addAttribute("usersPhone", usersPhone);
    model.addAttribute("voteBoxDto", voteBoxDto);
    return "users/count/vote-count";
  }

  //    @PostMapping("/save")
//    public String countSave(@RequestParam Long voteId, @RequestParam boolean isAgreed, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
//        if (countService.hadVoted(session, voteId)) {
//            throw new RuntimeException("이미 투표를 완료하였습니다.");
//        }
//        countService.save(isAgreed, voteId);
//        countService.confirmVoted(session, voteId);
//
//        redirectAttributes.addFlashAttribute("message", "save success");
//        Long electionId = voteService.detail(voteId).getElection().getElectionId();
//        return "redirect:/users/count/detail/" + electionId;
//    }
  @PostMapping("/save")
  public String saveVote(
    RedirectAttributes redirectAttributes, @RequestParam(required = false) List<Boolean> hadChosen,
    @RequestParam(required = false) List<Integer> scores,
    @RequestParam(required = false) List<Integer> ranks, VoteBoxDto voteBoxDto, @RequestParam String usersPhone) {
    if (voteBoxDto.getVoteType() == VoteType.CHOICE) {
      voteBoxDto.setChoices(hadChosen);
    } else if (voteBoxDto.getVoteType() == VoteType.SCORE) {
      if (scores != null && !scores.isEmpty()) {
        for (Integer score : scores) {
          voteBoxDto.addScore(score);
        }
      }
    } else if (voteBoxDto.getVoteType() == VoteType.PREFERENCE) {
      if (ranks != null && !ranks.isEmpty()) {
        for (Integer rank : ranks) {
          voteBoxDto.addRank(rank);
        }
      }
    }

    voteBoxService.save(voteBoxDto, usersPhone);

    redirectAttributes.addFlashAttribute("message", "save success");
    return "redirect:/users/count/detail/" + voteBoxDto.getElectionId();
  }

//    @GetMapping("/count/voteResult/{voteId}")
//    public String voteResult(Model model, @PathVariable Long voteId) {
//        Vote voteResult = countService.countVotesResultConfirm(voteId);
//        model.addAttribute("voteResult", voteResult);
//        return "users/count/vote-result";
//    }
}


