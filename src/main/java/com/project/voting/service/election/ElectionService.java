package com.project.voting.service.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.dto.election.ElectionDto;

import java.io.IOException;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ElectionService {

  Page<Election> getElectionList(Pageable pageable);

  Election addElectionAndVote(ElectionDto electionDto, Admin admin, MultipartFile file, List<String> voteTypes)
    throws IOException;

  void deleteElection(Long electionId);


  Election detail(Long electionId);


}
