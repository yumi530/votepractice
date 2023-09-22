package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;

import com.project.voting.domain.vote.VoteType;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

  private final CountRepository countRepository;
  private final CandCountRepository candCountRepository;

  @Override
  @Transactional
  public Count votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {

    List<CandCount> candCounts = candCountRepository.findByResult(true);

    CandCount candidateId = candCountRepository.findCandidateIdByVoteId(voteId);
    List<Integer> totalRanks = candCountRepository.findTotalRankByCandidateId(candidateId.getCandidateId());
    //수정 필요

    if (voteType == VoteType.PROS_CONS) {

        Count count = new Count();
        count.setElectionId(electionId);
        count.setVoteId(voteId);
        count.setCandidateId(candidateId.getCandidateId());
        count.setElectionId(candidateId.getCandidateId());

      for (CandCount candCount : candCounts) {
        count.setFinalResult(candCount.isResult());
      }
        countRepository.save(count);

    } else if (voteType == VoteType.CHOICE) {

      for (CandCount candCount : candCounts) {
        Count count = new Count();
        count.setElectionId(electionId);
        count.setVoteId(voteId);
        count.setCandidateId(candCount.getCandidateId());
        count.setElectionId(candCount.getCandidateId());
        count.setFinalResult(candCount.isResult());
        countRepository.save(count);
      }
      for (Integer totalRank : totalRanks) {
        Count count = new Count();
        count.setTotalRank(totalRank);
        countRepository.save(count);
      }
    }
    return new Count();
  }
}
