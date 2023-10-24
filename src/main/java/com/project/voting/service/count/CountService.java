package com.project.voting.service.count;


import com.project.voting.domain.count.Count;
import com.project.voting.domain.vote.VoteType;
import java.util.List;

public interface CountService {

  Count votesResultConfirm(Long electionId, Long voteId, VoteType voteType);

  List<Count> details(Long voteId);


}
