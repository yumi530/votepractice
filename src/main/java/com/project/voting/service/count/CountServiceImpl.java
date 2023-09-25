package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;

import com.project.voting.domain.vote.VoteType;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

  private final CountRepository countRepository;
  private final CandCountRepository candCountRepository;

  @Override

  public Count votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {

    List<CandCount> candCounts = candCountRepository.findByResult(true);

    List<CandCount> candidates = candCountRepository.findAllCandidateIdsByVoteId(voteId);
    for(CandCount candidate : candidates) {

      if (voteType == VoteType.PROS_CONS) {

        Count count = new Count();
        count.setElectionId(electionId);
        count.setVoteId(voteId);
        count.setCandidateId(candidate.getCandidateId());
        count.setElectionId(candidate.getCandidateId());

        for (CandCount candCount : candCounts) {
          count.setFinalResult(candCount.isResult());
        }


          countRepository.save(count);



      } else if (voteType == VoteType.CHOICE) {
        List<CandCount> totalRanks = candCountRepository.findTotalRankByCandidateId(candidate.getCandidateId());

        for (CandCount candCount : candCounts) {
          Count count = new Count();
          count.setElectionId(electionId);
          count.setVoteId(voteId);
          count.setCandidateId(candCount.getCandidateId());
          count.setElectionId(candCount.getCandidateId());
          count.setFinalResult(candCount.isResult());

          for (CandCount totalRank : totalRanks) {
            if (totalRank.getCandidateId().equals(candidate.getCandidateId())) {
              count.setTotalRank(totalRank.getTotalRank());
              break;
            }
          }
          countRepository.save(count);
        }

      }
    }
    return new Count();
  }
}
