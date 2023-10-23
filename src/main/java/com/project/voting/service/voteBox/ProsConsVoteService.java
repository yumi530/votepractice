package com.project.voting.service.voteBox;

import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import org.springframework.stereotype.Service;

@Service
public class ProsConsVoteService extends CommonVoteService {

  protected ProsConsVoteService(VoteBoxRepository voteBoxRepository,
    CandidateRepository candidateRepository, VoteBoxSaver voteSaver) {
    super(voteBoxRepository, candidateRepository, voteSaver);
  }
  public void doSaveProsCons(VoteBoxDto voteBoxDto) {

    isValid(voteBoxDto);
    doSave(voteBoxDto);

  }
}