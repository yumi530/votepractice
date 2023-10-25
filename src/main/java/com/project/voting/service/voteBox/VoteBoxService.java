package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.dto.voteBox.VoteBoxDto;

public interface VoteBoxService {

  void saveVote(VoteBoxDto voteBoxDto);
  VoteType getVoteType();

}