package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
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
public abstract class CountService {

  @Autowired
  ElectionRepository electionRepository;
  @Autowired
  CountRepository countRepository;

  public abstract void votesResultConfirm(Long electionId, Long voteId, VoteType voteType);

  public abstract boolean compareAvg(CandCount candidate1, CandCount candidate2);

  public abstract VoteType getVoteType();

  public boolean isValidCount(Long electionId) {

    Optional<Election> optionalElection = electionRepository.findById(electionId);
    Election election = optionalElection.orElseThrow(() -> new ElectionCustomException(
      ElectionErrorCode.ELECTION_NOT_GENERATED));
    LocalDateTime now = LocalDateTime.now();
    if (election.getElectionEndDt().isAfter(now)) {
      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
    }
    return true;
  }

  public List<Count> details(Long voteId) {
    return countRepository.findAllCandidateIdsByVoteId(voteId);
  }

  public Count createCount(Long electionId, Long voteId, Long candidateId) {

    Count count = new Count();
    count.setElectionId(electionId);
    count.setVoteId(voteId);
    count.setCandidateId(candidateId);
    return count;
  }
  public List<CandCount> sortCandidatesByAvg(List<CandCount> candidates) {
    int n = candidates.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = i + 1; j < n; j++) {
        CandCount candidate1 = candidates.get(i);
        CandCount candidate2 = candidates.get(j);

        if (compareAvg(candidate1, candidate2)) {
          candidates.set(i, candidate2);
          candidates.set(j, candidate1);
        }
      }
    }
    return candidates;
  }
}
