package com.project.voting.service.cand_count;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.service.voteBox.VoteBoxService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CandCountFactory {
  public final Map<VoteType, CandCountService> candCountTypeMap = new HashMap<>();

  public CandCountFactory(List<CandCountService> candCountServices) {
    for (CandCountService candCountService : candCountServices) {
      candCountTypeMap.put(candCountService.getVoteType(), candCountService);
    }
  }
  public CandCountService getService(VoteType voteType) {
    return candCountTypeMap.get(voteType);
  }
}
