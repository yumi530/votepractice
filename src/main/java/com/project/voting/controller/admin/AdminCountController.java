package com.project.voting.controller.admin;


import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.service.cand_count.CandCountService;
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

  private final CandCountService candCountService;

  @RequestMapping("/election/results")
  public String countVoteForAdmin(@RequestParam Long voteId, @RequestParam Long electionId,
    Model model, RedirectAttributes redirectAttributes, @RequestParam(name = "voteType")
  VoteType voteType, Long candidateId, String usersPhone) {
    CandCount candCount = candCountService.countVotesResult(voteId, electionId, candidateId, usersPhone, voteType);
    model.addAttribute("voteResult", candCount);
    redirectAttributes.addFlashAttribute("message", "개표 완료");
    return "/admin/election/results";
  }
}
