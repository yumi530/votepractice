package com.project.voting.service.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.election.ElectionDto;

import com.project.voting.dto.vote.VoteDto;
import java.util.List;

public interface ElectionService {
  List<Election> getElectionList();
  Election addElection(ElectionDto electionDto, Admin admin);



  void deleteElection(Long electionId);

  Election countElection(Long electionId);

  Election detail(Long electionId);

  Election save(ElectionDto electionDto);

  Election openedElection(ElectionDto electionDto);
}
