package com.project.voting.controller.count;


import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.election.Election;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class CountController {

  private final ElectionService electionService;
  private final UsersService usersService;
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
  public String detail(Model model, @PathVariable Long electionId,
    @RequestParam String usersPhone) {
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
  public String voteCount(Model model, @PathVariable(name = "voteId") Long voteId,
    @RequestParam(name = "usersPhone") String usersPhone) {
    Election detailElection = electionService.detailElection(voteId);
    Vote detailVote = voteService.detail(voteId);
    List<Candidate> detailCand = candidateService.detail(voteId);
    List<VoteBox> detailVoteBox = voteBoxService.detailVoteBox(voteId);
    Candidate candidateLength = candidateService.candidateLength(voteId);

    VoteBoxDto voteBoxDto = new VoteBoxDto();
    voteBoxDto.setDetailVoteBox(detailVoteBox);

    model.addAttribute("detailCand", detailCand);
    model.addAttribute("detailVote", detailVote);
    model.addAttribute("detailElection", detailElection);
    model.addAttribute("usersPhone", usersPhone);
    model.addAttribute("voteBoxDto", voteBoxDto);
    model.addAttribute("candidateLength", candidateLength);
    return "users/count/vote-count";
  }

  @PostMapping("/save")
  public String saveVote(@RequestParam(required = false) List<Integer> scores,
    @RequestParam(required = false) List<Integer> ranks,
    @RequestParam(required = false) String choices, VoteBoxDto voteBoxDto,
    @RequestParam(name = "usersPhone") String usersPhone) {

    if (voteBoxDto.getVoteType() == VoteType.PROS_CONS) {
      voteBoxService.saveProsCons(voteBoxDto, usersPhone);
    } else if (voteBoxDto.getVoteType() == VoteType.CHOICE) {
      voteBoxService.saveChoice(voteBoxDto, usersPhone, choices);
    }

    if (voteBoxDto.getVoteType() == VoteType.SCORE) {

      if (scores != null && !scores.isEmpty()) {
        for (Integer score : scores) {
          voteBoxDto.addScore(score);
        }
        voteBoxService.saveScore(voteBoxDto, usersPhone);
      }
    } else if (voteBoxDto.getVoteType() == VoteType.PREFERENCE) {

      if (ranks != null && !ranks.isEmpty()) {
        for (Integer rank : ranks) {
          voteBoxDto.addRank(rank);
        }
        voteBoxService.savePrefer(voteBoxDto, usersPhone);
      }
    }

    String redirectUrl =
      "redirect:/users/count/detail/" + voteBoxDto.getElectionId() + "?usersPhone=" + usersPhone;
    return redirectUrl;
  }
}


