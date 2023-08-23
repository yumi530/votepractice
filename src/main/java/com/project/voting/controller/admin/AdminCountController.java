package com.project.voting.controller.admin;


import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.service.count.CountService;
import com.project.voting.service.election.ElectionService;
import com.project.voting.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCountController {

  private final ElectionService electionService;
  private final CountService countService;
  private final VoteService voteService;

//  @GetMapping("/election/results")
//  public String showResultsForAdmin(Long voteId, Model model) {
//    Vote votes = countService.countVotes(voteId);
//    model.addAttribute("votes", votes);
//    return "admin/election/election-results";
//  }

  @GetMapping("/election/results")
  public String countVoteForAdmin(@RequestParam Long voteId, @RequestParam Long electionId,
    RedirectAttributes redirectAttributes, Model model) {
    Vote vote = countService.countVotesResult(voteId, electionId);
    model.addAttribute("voteResult", vote);
    redirectAttributes.addFlashAttribute("message", "개표 완료");
    return "admin/election/election-results";
  }

//  @PostMapping("/election/results")
//  public String countVoteForAdmin(Long voteId, Long electionId,
//    RedirectAttributes redirectAttributes, Model model) {
//    Vote vote = countService.countVotesResult(voteId, electionId);
//    model.addAttribute("voteResult", vote);
//    redirectAttributes.addFlashAttribute("message", "개표 완료");
//    return "/admin/election/election-results-confirm";
//  }
}
