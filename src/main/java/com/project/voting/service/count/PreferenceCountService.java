package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.vote.VoteType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreferenceCountService extends CountService {
  @Autowired
  CandCountRepository candCountRepository;
  @Autowired
  CountRepository countRepository;

  @Override
  public void votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {
    List<CandCount> candidateIds = candCountRepository.findAllCandidateIdsByVoteId(voteId);

    List<CandCount> sortedCandidates = sortCandidatesByAvg(candidateIds);

    for (int i = 0; i < sortedCandidates.size(); i++) {
      CandCount candidate = sortedCandidates.get(i);
      Count count = createCount(electionId, voteId, candidate.getCandidateId());
      count.setTotalRank(i + 1);

      countRepository.save(count);
    }
  }
  @Override
  public VoteType getVoteType() {
    return VoteType.PREFERENCE;
  }
  @Override
  public boolean compareAvg(CandCount candidate1, CandCount candidate2) {
    return candidate1.getRanksAvg() < candidate2.getRanksAvg();
  }
}
