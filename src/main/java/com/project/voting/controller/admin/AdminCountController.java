package com.project.voting.controller.admin;


import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.service.cand_count.CandCountService;
import com.project.voting.service.candidate.CandidateService;
import com.project.voting.service.count.CountService;
import com.project.voting.service.vote.VoteService;
import com.project.voting.service.voteBox.VoteBoxService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCountController {

  private final CandCountService candCountService;
  private final VoteService voteService;
  private final CandidateService candidateService;
  private final CountService countService;

  @RequestMapping("/election/results")
  public String countVoteForAdmin(@RequestParam Long voteId, @RequestParam Long electionId,
    Model model, RedirectAttributes redirectAttributes, @RequestParam(name = "voteType")
  VoteType voteType) {
    CandCount candCount = candCountService.countVotesResult(voteId, electionId, voteType);
    Vote vote = voteService.detail(voteId);
    List<Candidate> candidates = candidateService.details(voteId);
//    countService.votesResultConfirm(voteId, electionId, voteType);
    model.addAttribute("candCount", candCount);
    model.addAttribute("votes", vote);
    model.addAttribute("candidates", candidates);

    redirectAttributes.addFlashAttribute("message", "개표 완료");
    return "/admin/election/results";
  }

  @RequestMapping("/result")
  public String sample(@RequestParam Long voteId, @RequestParam Long electionId, @RequestParam VoteType voteType) {
    Count count = countService.votesResultConfirm(voteId, electionId, voteType);
    return "/admin/election/sample";
  }
}
