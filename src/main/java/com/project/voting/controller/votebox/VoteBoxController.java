package com.project.voting.controller.votebox;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.service.candidate.CandidateService;
import com.project.voting.service.election.ElectionService;
import com.project.voting.service.vote.VoteService;
import java.util.List;

import com.project.voting.service.voteBox.VoteBoxService;
import com.project.voting.service.voteBox.VoteBoxServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class VoteBoxController {

  private final ElectionService electionService;
  private final VoteService voteService;
  private final CandidateService candidateService;
  private final VoteBoxServiceFactory voteBoxServiceFactory;

  @GetMapping("/count/voteCount/{voteId}")
  public String voteCount(Model model, @PathVariable(name = "voteId") Long voteId,
    @RequestParam(name = "usersPhone") String usersPhone) {
    Election detailElection = electionService.detailElection(voteId);
    Vote detailVote = voteService.detail(voteId);
    List<Candidate> detailCand = candidateService.detail(voteId);

    List<VoteBox> getVoteBoxInfo = voteBoxServiceFactory.getService(detailVote.getVoteType()).getVoteBoxInfo(voteId);

    VoteBoxDto voteBoxDto = new VoteBoxDto();
    voteBoxDto.setDetailVoteBox(getVoteBoxInfo);

    model.addAttribute("detailCand", detailCand);
    model.addAttribute("detailVote", detailVote);
    model.addAttribute("detailElection", detailElection);
    model.addAttribute("usersPhone", usersPhone);
    model.addAttribute("voteBoxDto", voteBoxDto);

    return "users/count/vote-count";
  }

  @PostMapping("/save")
  public String saveVote(VoteBoxDto voteBoxDto) {

    VoteBoxService voteBoxService =  voteBoxServiceFactory.getService(voteBoxDto.getVoteType());
    voteBoxService.isValid(voteBoxDto, voteBoxDto.getUsersPhone());
    voteBoxService.saveVote(voteBoxDto);

    return "redirect:/users/count/detail/" + voteBoxDto.getElectionId() + "?usersPhone="
      + voteBoxDto.getUsersPhone();
  }

}
