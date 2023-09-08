package com.project.voting.controller.admin;


import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.service.count.CountService;
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

  private final CountService countService;

  @RequestMapping("/election/results")
  public String countVoteForAdmin(@RequestParam Long voteId, @RequestParam Long electionId , Model model, RedirectAttributes redirectAttributes, @RequestParam(name = "voteType")
  VoteType voteType, String candidateName) {
    Vote vote = countService.countVotesResult(voteId, electionId ,voteType, candidateName);
    model.addAttribute("voteResult", vote);
    redirectAttributes.addFlashAttribute("message", "개표 완료");
    return "/admin/election/results";
  }
}
