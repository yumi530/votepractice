package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class VoteBoxServiceFactory {

  private final Map<VoteType, VoteBoxService> voteBoxServiceMap = new HashMap<>();

  /**
   * 생성자주입
   * @param voteBoxServices
   */
  public VoteBoxServiceFactory(List<VoteBoxService> voteBoxServices) {
    voteBoxServices.forEach(s -> voteBoxServiceMap.put(s.getVoteType(), s));
  }

  public VoteBoxService getVoteBoxService(VoteType voteType) {
    return voteBoxServiceMap.get(voteType);
  }

//  public VoteBoxService newInstance(VoteBoxDto voteBoxDto) {
//    VoteBoxService voteBoxService = saveVoteBox();
//    voteBoxService.saveVote(voteBoxDto);
//    return voteBoxService;
//  }
//
//  protected abstract VoteBoxService saveVoteBox();

}
