package com.project.voting.controller.count;


import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.users.UsersDto;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.service.cand_count.CandCountService;
import com.project.voting.service.candidate.CandidateService;
import com.project.voting.service.count.CountService;
import com.project.voting.service.election.ElectionService;
import com.project.voting.service.users.UsersService;
import com.project.voting.service.vote.VoteService;
import com.project.voting.service.voteBox.CommonVoteService;
//import com.project.voting.service.voteBox.DefaultVoteService;

import com.project.voting.service.voteBox.DefaultVoteService;
import com.project.voting.service.voteBox.ProsConsVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
//  private final VoteBoxService voteBoxService;
  private final CommonVoteService commonVoteService;

  private final DefaultVoteService defaultVoteService;
  private final ProsConsVoteService prosConsVoteService;
  private final CountService countService;
  private final CandCountService candCountService;


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
    List<VoteBox> getVoteBoxInfo = commonVoteService.getVoteBoxInfo(voteId);


    VoteBoxDto voteBoxDto = new VoteBoxDto();
    voteBoxDto.setDetailVoteBox(getVoteBoxInfo);

    model.addAttribute("detailCand", detailCand);
    model.addAttribute("detailVote", detailVote);
    model.addAttribute("detailElection", detailElection);
    model.addAttribute("usersPhone", usersPhone);
    model.addAttribute("voteBoxDto", voteBoxDto);


    return "users/count/vote-count";
  }

  @GetMapping("/count/voteResult/{voteId}")
  public String countVoteComplete(@RequestParam Long electionId, @PathVariable Long voteId, @RequestParam VoteType voteType, Model model, @RequestParam(name = "usersPhone") String usersPhone) {
    countService.votesResultConfirm(electionId, voteId, voteType);

    Vote vote = voteService.detail(voteId);
    Election election = electionService.detail(electionId);
    List<Candidate> candidates = candidateService.details(voteId);
    List<Count> counts = countService.details(voteId);
    List<CandCount> candCounts = candCountService.details(voteId);

    model.addAttribute("usersPhone", usersPhone);
    model.addAttribute("votes", vote);
    model.addAttribute("elections", election);
    model.addAttribute("candidates", candidates);
    model.addAttribute("counts", counts);
    model.addAttribute("candCounts", candCounts);

    return "users/count/vote-result";
  }

  @PostMapping("/save")
  public String saveVote(VoteBoxDto voteBoxDto) {

    if (voteBoxDto.getVoteType().equals(VoteType.PROS_CONS)) {
      prosConsVoteService.doSaveProsCons(voteBoxDto);
    }
    else {
      defaultVoteService.doSaveDefault(voteBoxDto);
    }

    return "redirect:/users/count/detail/" + voteBoxDto.getElectionId() + "?usersPhone=" + voteBoxDto.getUsersPhone();
  }
}


