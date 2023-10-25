package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.vote.VoteType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreferenceCountService implements CountService {

  private final CandCountRepository candCountRepository;
  private final CountRepository countRepository;
  private final CommonCountService commonCountService;

  @Override
  public void votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {

    boolean isValid = commonCountService.isValidCount(electionId, voteId, voteType);
if (isValid) {
  List<CandCount> candidateIds = candCountRepository.findAllCandidateIdsByVoteId(voteId);

  for (CandCount candidate : candidateIds) {
    Count count = commonCountService.createCount(electionId, voteId, candidate.getCandidateId());

    List<CandCount> sortedCandidates = sortCandidatesByAvg(candidateIds);
    count.setTotalRank(sortedCandidates.indexOf(candidate) + 1);

    countRepository.save(count);
  }
}
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.PREFERENCE;
  }

  public List<CandCount> sortCandidatesByAvg(List<CandCount> candidates) {
    int n = candidates.size();
    for (int i = 0; i < n - 1; i++) {
      int maxIndex = i;
      for (int j = i + 1; j < n; j++) {
          if (candidates.get(j).getRanksAvg() < candidates.get(maxIndex).getRanksAvg()) {
            maxIndex = j;
          }
      }
      if (maxIndex != i) {
        CandCount tmp = candidates.get(i);
        candidates.set(i, candidates.get(maxIndex));
        candidates.set(maxIndex, tmp);
      }
    }
    return candidates;
  }
}
