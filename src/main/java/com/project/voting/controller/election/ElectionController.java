package com.project.voting.controller.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.dto.vote.VoteDto;
import com.project.voting.service.admin.AdminService;
import com.project.voting.service.election.ElectionServiceImpl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ElectionController {

  private final ElectionServiceImpl electionService;
  private final AdminService adminService;

  @GetMapping("/electionList")
  public String getVoteList(Model model){
    List<Election> electionList = electionService.getElectionList();
    model.addAttribute("electionList", electionList);
    return "admin/election_list";
  }

  @GetMapping("/insert")
  public String addElection() {
    return "admin/insert";
  }
  @PostMapping("/insert")
  public String addElectionSubmit(Model model, ElectionDto electionDto, Admin admin) {
    Election addElection = electionService.addElection(electionDto, admin);

    model.addAttribute("addElection", addElection);
    return "admin/election_list";
  }


  @PostMapping("/detail")
  public String voteDetail(Model model, Long voteId){
    Election detail = electionService.detail(voteId);
    model.addAttribute("detail", detail);
    return "admin/detail";

  }
  @PostMapping("/election/save")
  public ResponseEntity<Election> saveVote(@RequestBody ElectionDto electionDto) {
    try {
      Election savedElection = electionService.save(electionDto);
      return ResponseEntity.ok(savedElection);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
