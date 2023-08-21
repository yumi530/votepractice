package com.project.voting.controller.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.service.admin.AdminService;
import com.project.voting.service.election.ElectionServiceImpl;

import com.project.voting.service.vote.VoteService;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ElectionController {

    private final ElectionServiceImpl electionService;
    private final AdminService adminService;
    private final VoteService voteService;

    @RequestMapping("/electionList")
    public String getVoteList(Model model, @PageableDefault(page = 0, size = 10, sort = "electionId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Election> electionList = electionService.getElectionList(pageable);

        int nowPage = electionList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, electionList.getTotalPages());

        model.addAttribute("electionList", electionList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "admin/election/election-list";
    }

    @GetMapping("/insert")
    public String addElection() {
        return "admin/insert";
    }

    @PostMapping("/election")
    public String addElectionSubmit(ElectionDto electionDto, @AuthenticationPrincipal Admin admin, RedirectAttributes redirectAttributes, @RequestPart MultipartFile file) throws IOException {
        electionService.addElectionAndVote(electionDto, admin, file);
        redirectAttributes.addFlashAttribute("message", "addElectionAndVote Success");
        return "redirect:/admin/electionList";
    }

    @GetMapping("/election/detail")
    public String electionDetail(Model model, ElectionDto electionDto) {
        Election detail = electionService.detail(electionDto);
        model.addAttribute("detail", detail);
        return "admin/election/election-detail";
    }

    @DeleteMapping("/election/delete")
    public ResponseEntity deletedElection(Long electionId) {
        electionService.deleteElection(electionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
