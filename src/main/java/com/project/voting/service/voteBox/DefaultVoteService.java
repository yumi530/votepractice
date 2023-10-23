package com.project.voting.service.voteBox;


import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import org.springframework.stereotype.Service;

@Service
public class DefaultVoteService extends CommonVoteService {

  protected DefaultVoteService(VoteBoxRepository voteBoxRepository,
    CandidateRepository candidateRepository, VoteBoxSaver voteSaver) {
    super(voteBoxRepository, candidateRepository, voteSaver);
  }

  public void doSaveDefault(VoteBoxDto voteBoxDto) {

    isValid(voteBoxDto);
    doSave(voteBoxDto);

  }

}


