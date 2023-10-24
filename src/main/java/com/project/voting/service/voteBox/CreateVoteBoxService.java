package com.project.voting.service.voteBox;

import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public abstract class CreateVoteBoxService implements VoteBoxService{

  @Autowired
  VoteBoxRepository voteBoxRepository;
  @Autowired
  CandidateRepository candidateRepository;


//   공통 메서드 정의

  public VoteBox createVoteBox(VoteBoxDto voteBoxDto) {
    VoteBox voteBox = new VoteBox();
    voteBox.setElectionId(voteBoxDto.getElectionId());
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    return voteBox;
  }

//  public List<VoteBox> getVoteBoxInfo(Long voteId) {
//    return voteBoxRepository.findAllByVoteId(voteId);
//  }
//
//  public boolean isValid(VoteBoxDto voteBoxDto) {
//    Optional<VoteBox> optionalVoteBox = voteBoxRepository.findByCandidateIdAndUsersPhone(
//      voteBoxDto.getCandidateId(), voteBoxDto.getUsersPhone());
//    if (optionalVoteBox.isPresent()) {
//      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_DUPLICATED);
//    }
//    List<Candidate> candidateList = candidateRepository.findAllCandidateIdByVoteId(
//      voteBoxDto.getVoteId());
//    if (candidateList == null) {
//      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_NOT_FOUND);
//    }
//    return true;
//  }

}
