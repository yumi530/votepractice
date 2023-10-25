package com.project.voting.service.count;

import com.project.voting.domain.vote.VoteType;


public interface CountService {

  void votesResultConfirm(Long electionId, Long voteId, VoteType voteType);

  VoteType getVoteType();


}
