package com.project.voting.service.voteBox;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.exception.vote_box.VoteBoxCustomException;
import com.project.voting.exception.vote_box.VoteBoxErrorCode;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public abstract class CommonVoteBoxService implements VoteBoxService{

  @Autowired
  VoteBoxRepository voteBoxRepository;
  @Autowired
  CandidateRepository candidateRepository;

  public VoteBox createVoteBox(VoteBoxDto voteBoxDto) {
    VoteBox voteBox = VoteBox.builder()
      .electionId(voteBoxDto.getElectionId())
      .voteId(voteBoxDto.getVoteId())
      .usersPhone(voteBoxDto.getUsersPhone())
      .build();
    return voteBox;
  }

  public boolean isValid(VoteBoxDto voteBoxDto) {
    Optional<VoteBox> optionalVoteBox = voteBoxRepository.findByCandidateIdAndUsersPhone(
      voteBoxDto.getCandidateId(), voteBoxDto.getUsersPhone());
    if (optionalVoteBox.isPresent()) {
      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_DUPLICATED);
    }
    List<Candidate> candidateList = candidateRepository.findAllCandidateIdByVoteId(
      voteBoxDto.getVoteId());
    if (candidateList == null) {
      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_NOT_FOUND);
    }
    return true;
  }

}
