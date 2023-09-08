package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.dto.users.UsersDto;

import java.util.List;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public interface CountService {

    boolean hadVoted(HttpSession session, Long voteId);

    void confirmVoted(HttpSession session, Long voteId);

    Count save(boolean isAgreed, Long voteId);

    Vote countVotesResult(Long voteId, Long electionId, VoteType voteType, String candidateName);

    Vote countVotesResultConfirm(Long voteId);


}
