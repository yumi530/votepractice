package com.project.voting.controller.admin;


import com.project.voting.domain.vote.Vote;
import com.project.voting.service.count.CountService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCountController {

  private final CountService countService;

  @GetMapping("/election/results")
  public String countVoteForAdmin(@RequestParam Long voteId, @RequestParam Long electionId,
    RedirectAttributes redirectAttributes, Model model) {
    Vote vote = countService.countVotesResult(voteId, electionId);
    model.addAttribute("voteResult", vote);
    redirectAttributes.addFlashAttribute("message", "개표 완료");
    return "/admin/election/results";
  }
}
