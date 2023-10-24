package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.vote.VoteType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CountSorter {

  public List<CandCount> sortCandidatesByAvg(List<CandCount> candidates, VoteType voteType) {
    int n = candidates.size();
    for (int i = 0; i < n - 1; i++) {
      int maxIndex = i;
      for (int j = i + 1; j < n; j++) {
        if (voteType == VoteType.SCORE) {
          if (candidates.get(j).getScoresAvg() > candidates.get(maxIndex).getScoresAvg()) {
            maxIndex = j;
          }
        } else if (voteType == VoteType.PREFERENCE) {
          if (candidates.get(j).getRanksAvg() < candidates.get(maxIndex).getRanksAvg()) {
            maxIndex = j;
          }
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

  public List<CandCount> sortCandidatesByChoiceAvg(List<CandCount> candidates) {
    int n = candidates.size();
    for (int i = 0; i < n - 1; i++) {
      int maxIndex = i;
      for (int j = i + 1; j < n; j++) {
        if (candidates.get(j).getChoicesAvg() > candidates.get(maxIndex).getChoicesAvg()) {
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
