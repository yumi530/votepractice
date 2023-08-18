package com.project.voting.service.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.election.ElectionDto;

import com.project.voting.dto.vote.VoteDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ElectionService {
  Page<Election> getElectionList(Pageable pageable);

  @Transactional
  Election createdElection();

  Election addElection(ElectionDto electionDto, Admin admin);



  void deleteElection(Long electionId);

  Election countElection(Long electionId);

  Election detail(Long electionId);

  Election save(ElectionDto electionDto);

  Election openedElection(ElectionDto electionDto);
}
