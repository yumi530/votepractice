package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class VoteBoxServiceFactory {

  public final Map<VoteType, VoteBoxService> voteBoxTypeMap = new HashMap<>();

  public VoteBoxServiceFactory(List<VoteBoxService> voteBoxServices) {
    for (VoteBoxService voteBoxService : voteBoxServices) {
      voteBoxTypeMap.put(voteBoxService.getVoteType(), voteBoxService);
    }
  }
  public VoteBoxService getService(VoteType voteType) {
    return voteBoxTypeMap.get(voteType);
  }
}

