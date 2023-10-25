package com.project.voting.service.voteBox;

import com.project.voting.dto.voteBox.VoteBoxDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainVoteBoxService {

  private final VoteBoxServiceFactory voteBoxServiceFactory;

  public void saveVote(VoteBoxDto voteBoxDto)
  {
    voteBoxServiceFactory.getService(voteBoxDto.getVoteType()).saveVote(voteBoxDto);
  }

}