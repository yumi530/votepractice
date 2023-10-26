package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.cand_count.CandCountErrorCode;
import com.project.voting.exception.election.ElectionCustomException;
import com.project.voting.exception.election.ElectionErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class CandCountService {

  @Autowired
  ElectionRepository electionRepository;
  @Autowired
  VoteBoxRepository voteBoxRepository;
  @Autowired
  CandCountRepository candCountRepository;

  public abstract void countVotesResult(Long voteId, Long electionId);

  public abstract VoteType getVoteType();

  public List<CandCount> getDetails(Long voteId) {
    return candCountRepository.findAllCandidateIdsByVoteId(voteId);
  }

  public boolean isValidCandCount(Long electionId){

    Optional<Election> optionalElection = electionRepository.findById(electionId);
    Election election = optionalElection.orElseThrow(
      () -> new ElectionCustomException(ElectionErrorCode.ELECTION_NOT_GENERATED));

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
    }
    return true;
  }

  public CandCount createCandCount(Long voteId, Long electionId) {

    CandCount candCount = new CandCount();
    candCount.setElectionId(electionId);
    candCount.setVoteId(voteId);
    return candCount;
  }

  public double calculateUsersNum(Long candidateId) {
    double usersSum = 0.0;
    List<VoteBox> voteBoxes = voteBoxRepository.findAllByCandidateId(candidateId);
    for (VoteBox voteBox : voteBoxes) {
      usersSum += 1.0;
    }
    return usersSum;
  }

}
