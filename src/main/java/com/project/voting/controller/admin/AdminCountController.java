package com.project.voting.controller.admin;


import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.service.cand_count.CandCountService;
import com.project.voting.service.cand_count.MainCandCountService;
import com.project.voting.service.candidate.CandidateService;
import com.project.voting.service.count.CountService;
import com.project.voting.service.count.MainCountService;
import com.project.voting.service.election.ElectionService;
import com.project.voting.service.vote.VoteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCountController {

  private final VoteService voteService;
  private final CandidateService candidateService;
  private final MainCountService mainCountService;
  private final ElectionService electionService;
  private final MainCandCountService mainCandCountService;

  @RequestMapping("/election/results")
  public String countVoteForAdmin(@RequestParam Long voteId, @RequestParam Long electionId,
    Model model, RedirectAttributes redirectAttributes, @RequestParam(name = "voteType")
  VoteType voteType) {

    mainCandCountService.countVotesResult(voteId, electionId, voteType);

    Vote vote = voteService.detail(voteId);
    Election election = electionService.detail(electionId);
    List<Candidate> candidates = candidateService.details(voteId);
//    Count turnOut = countService.turnOut(electionId ,voteId);

    model.addAttribute("votes", vote);
    model.addAttribute("elections",election);
    model.addAttribute("candidates", candidates);
//    model.addAttribute("turnOut", turnOut);

    redirectAttributes.addFlashAttribute("message", "개표 완료");
    return "/admin/election/results";
  }

  @RequestMapping("/election/complete")
  public String countVoteComplete(@RequestParam Long electionId, @RequestParam Long voteId,  @RequestParam VoteType voteType, Model model) {

    mainCountService.votesResultConfirm(electionId, voteId, voteType);

    Vote vote = voteService.detail(voteId);
    Election election = electionService.detail(electionId);
    List<Candidate> candidates = candidateService.details(voteId);
    List<Count> counts = mainCountService.details(voteId);
    List<CandCount> candCounts = mainCandCountService.details(voteId);

    model.addAttribute("votes", vote);
    model.addAttribute("elections",election);
    model.addAttribute("candidates", candidates);
    model.addAttribute("counts", counts);
    model.addAttribute("candCounts", candCounts);

    return "/admin/election/complete";
  }
}