package com.project.voting.controller.count;


import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.users.UsersDto;
import com.project.voting.service.count.CountService;
import com.project.voting.service.election.ElectionService;
import com.project.voting.service.users.UsersService;
import com.project.voting.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class CountController {

    private final ElectionService electionService;
    private final UsersService usersService;
    private final CountService countService;
    private final VoteService voteService;

    @GetMapping("/count/list")
    public String list(@AuthenticationPrincipal @RequestParam(name = "phoneNumber") String usersPhone, Model model) {
        List<UsersDto> usersDtoList = usersService.detailList(usersPhone);
        model.addAttribute("usersDtoList", usersDtoList);
        return "users/count/list";
    }

    @GetMapping("/count/detail/{electionId}")
    public String detail(Model model, @PathVariable Long electionId) {
        Election detail = electionService.detail(electionId);
        model.addAttribute("detail", detail);

        LocalDateTime currentDate = LocalDateTime.now();
//    LocalDateTime endDate = LocalDateTime.parse(detail.getElectionEndDt());
        LocalDateTime endDate = detail.getElectionEndDt();
        boolean isBefore = currentDate.isBefore(endDate);
        model.addAttribute("isBefore", isBefore);

        return "users/count/detail";
    }

    @GetMapping("/count/voteCount/{voteId}")
    public String voteCount(Model model, @PathVariable(name = "voteId") Long voteId) {
        Vote detail = voteService.detail(voteId);
        model.addAttribute("detailVote", detail);
        return "users/count/vote-count";
    }

//    @PostMapping("/save")
//    public String countSave(@RequestParam Long voteId, @RequestParam boolean isAgreed, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
//        if (countService.hadVoted(session, voteId)) {
//            throw new RuntimeException("이미 투표를 완료하였습니다.");
//        }
//        countService.save(isAgreed, voteId);
//        countService.confirmVoted(session, voteId);
//
//        redirectAttributes.addFlashAttribute("message", "save success");
//        Long electionId = voteService.detail(voteId).getElection().getElectionId();
//        return "redirect:/users/count/detail/" + electionId;
//    }

//    @GetMapping("/count/voteResult/{voteId}")
//    public String voteResult(Model model, @PathVariable Long voteId) {
//        Vote voteResult = countService.countVotesResultConfirm(voteId);
//        model.addAttribute("voteResult", voteResult);
//        return "users/count/vote-result";
//    }
}


