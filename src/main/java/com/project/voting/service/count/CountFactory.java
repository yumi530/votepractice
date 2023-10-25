package com.project.voting.service.count;

import com.project.voting.domain.vote.VoteType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CountFactory {

  public final Map<VoteType, CountService> countTypeMap = new HashMap<>();

  public CountFactory(List<CountService> countServices) {
    for (CountService countService : countServices) {
      countTypeMap.put(countService.getVoteType(), countService);
    }
  }
  public CountService getService(VoteType voteType) {
    return countTypeMap.get(voteType);
  }

}
