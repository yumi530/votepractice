package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.users.UsersDto;
import javax.servlet.http.HttpSession;

public interface CountService {


  boolean hadVoted(HttpSession session, Long voteId);

  void confirmVoted(HttpSession session, Long voteId);

  Count save(boolean isAgreed, Long voteId);

  Vote countVotes(Long voteId);

  Vote countVotesResult(Long electionId, Long voteId);

  Vote countVotesResultConfirm(Long voteId);

//  Users complete(UsersDto users);

}
