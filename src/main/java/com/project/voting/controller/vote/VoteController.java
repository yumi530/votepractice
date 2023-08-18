package com.project.voting.controller.vote;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.dto.vote.VoteDto;
import com.project.voting.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class VoteController {

  private final VoteService voteService;

  @PostMapping("/vote/save")
  public ResponseEntity<Vote> saveVote(@RequestBody VoteDto voteDto) {
    try {
      Vote savedVote = voteService.save(voteDto);
      return ResponseEntity.ok(savedVote);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

//  @GetMapping("/election/vote/detail")
//  public String voteDetail(Model model, VoteDto voteDto) {
//    Vote detailVote = voteService.detail(voteDto);
//    model.addAttribute("detailVote", detailVote);
//    return "admin/election-detail";
//  }
}
