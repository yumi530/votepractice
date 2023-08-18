package com.project.voting.controller.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.dto.vote.VoteDto;
import com.project.voting.service.admin.AdminService;
import com.project.voting.service.election.ElectionServiceImpl;

import com.project.voting.service.vote.VoteService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ElectionController {

  private final ElectionServiceImpl electionService;
  private final AdminService adminService;
  private final VoteService voteService;

  @RequestMapping("/electionList")
  public String getVoteList(Model model,
    @PageableDefault(page = 0, size = 10, sort = "electionId", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<Election> electionList = electionService.getElectionList(pageable);

    int nowPage = electionList.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 5, electionList.getTotalPages());

    model.addAttribute("electionList", electionList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);
    return "admin/election-list";
  }

  @GetMapping("/insert")
  public String addElection(Model model) {
    Election createdElection = electionService.createdElection();
    model.addAttribute("createdElection", createdElection);
    return "admin/insert";
  }

  @PostMapping("/election")
  public ResponseEntity addElectionSubmit(Model model,
    @ModelAttribute("createdElection") ElectionDto electionDto,
    @AuthenticationPrincipal Admin admin) {
    Election addElection = electionService.addElection(electionDto, admin);
    model.addAttribute("addElection", addElection);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/election/detail")
  public String electionDetail(Model model, ElectionDto electionDto,List<Long> voteId) {
    Election detail = electionService.detail(electionDto.getElectionId());
    List<Vote> detailVotes = voteService.detail(voteId);
    model.addAttribute("detailVotes", detailVotes);
    model.addAttribute("detail", detail);
    return "admin/election-detail";
  }
//  @GetMapping("/election/detail")
//  public String electionDetail(Model model, ElectionDto electionDto, List<Long> voteId) {
//    Election detail = electionService.detail(electionDto.getElectionId());
//    List<Vote> detailVote = voteService.detail(voteId);
//    model.addAttribute("detailVote", detailVote);
//    model.addAttribute("detail", detail);
//    return "admin/election-detail";
//  }

  //  @DeleteMapping("/election/delete")
//  public String deletedElection(Long electionId){
//    electionService.deleteElection(electionId);
//    return "admin/election-list";
//  }

  @DeleteMapping("/election/delete")
  public ResponseEntity deletedElection(Long electionId) {
    electionService.deleteElection(electionId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
