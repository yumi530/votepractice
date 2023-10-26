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
public class ProsConsCountService extends CountService {

  @Autowired
  CandCountRepository candCountRepository;
  @Autowired
  CountRepository countRepository;

  @Override
  public void votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {

    List<CandCount> candCounts = candCountRepository.findAllByResult(true);
    CandCount candidate = candCountRepository.findCandidateIdByVoteId(voteId);

    for (CandCount candCount : candCounts) {

      Count count = createCount(electionId, voteId, candidate.getCandidateId());
      count.setElectedYn(candCount.isResult());
      countRepository.save(count);
    }
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.PROS_CONS;
  }

  @Override
  public boolean compareAvg(CandCount candidate1, CandCount candidate2) {
    return false;
  }
}
