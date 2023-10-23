package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.vote.VoteType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CountSorter {

  public List<CandCount> sortCandidatesByAvg(List<CandCount> candidates, VoteType voteType) {

    if (voteType == VoteType.SCORE) {
      return candidates.stream()
        .sorted(Comparator.comparing(CandCount::getScoresAvg).reversed())
        .collect(Collectors.toList());
    } else if (voteType == VoteType.PREFERENCE) {
      return candidates.stream()
        .sorted(Comparator.comparing(CandCount::getRanksAvg))
        .collect(Collectors.toList());
    }
    return candidates;
  }

  public List<CandCount> sortCandidatesByChoiceAvg(List<CandCount> candidates) {
    return candidates.stream()
      .sorted(Comparator.comparing(CandCount::getChoicesAvg).reversed())
      .collect(Collectors.toList());
  }


}
