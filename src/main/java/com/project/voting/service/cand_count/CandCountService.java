package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.vote.VoteType;

public interface CandCountService {

  CandCount countVotesResult(Long voteId, Long electionId, VoteType voteType, Long candidateId);

}
