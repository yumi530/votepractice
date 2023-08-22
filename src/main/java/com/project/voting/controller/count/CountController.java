package com.project.voting.controller.count;


import com.project.voting.domain.users.Users;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.dto.users.UsersDto;
import com.project.voting.service.election.ElectionService;
import com.project.voting.service.users.UsersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class CountController {

  private final ElectionService electionService;
  private final UsersService usersService;

  @GetMapping("/count/list")
  public String countList(@AuthenticationPrincipal Users users, Model model){
    List<ElectionDto> electionDtoList = electionService.detailList(users.getUsersPhone());
    model.addAttribute("electionDtoList", electionDtoList);
    return "users/count/list";
  }

}
