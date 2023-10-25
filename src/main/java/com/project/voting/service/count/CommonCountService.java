package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.cand_count.CandCountErrorCode;
import com.project.voting.exception.election.ElectionCustomException;
import com.project.voting.exception.election.ElectionErrorCode;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class CommonCountService implements CountService {
  @Autowired
  ElectionRepository electionRepository;

  protected Count createCount(Long electionId, Long voteId, Long candidateId) {
    Count count = new Count();
    count.setElectionId(electionId);
    count.setVoteId(voteId);
    count.setCandidateId(candidateId);
    return count;
  }

  public boolean isValidCount(Long electionId, Long voteId, VoteType voteType) {
    //공통 밸리데이션으로 분리 하기
    Optional<Election> optionalElection = this.electionRepository.findById(electionId);
    Election election = optionalElection.orElseThrow(() -> new ElectionCustomException(
      ElectionErrorCode.ELECTION_NOT_GENERATED));

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
    }
    return true;
  }

}