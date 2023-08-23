package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.count.CountDto;

public interface CountService {

  Count save(boolean isAgreed, Long voteId, boolean hadVoted);

  Vote countVotes(Long voteId);

  Vote countVotesResult(Long electionId, Long voteId);

  Vote countVotesResultConfirm(Long voteId);

//  Users complete(Users users);
}
