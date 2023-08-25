package com.project.voting.service.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.election.ElectionDto;

import com.project.voting.dto.users.UsersDto;
import com.project.voting.dto.vote.VoteDto;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ElectionService {

  Page<Election> getElectionList(Pageable pageable);

  Election addElectionAndVote(ElectionDto electionDto, Admin admin, MultipartFile file)
    throws IOException;

  void deleteElection(Long electionId);

  Election countElection(Long electionId);

  List<ElectionDto> detailList(String userPhone);

  Election detail(Long electionId);

//  Election detail(Long electionId, String usersPhone, boolean isUsersCompleted);

}
