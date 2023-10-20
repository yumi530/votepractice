package com.project.voting.service.count;


import com.project.voting.domain.count.Count;
import com.project.voting.domain.vote.VoteType;
import java.util.List;
import javax.transaction.Transactional;

public interface CountService {

  @Transactional
  Count votesResultConfirm(Long electionId, Long voteId, VoteType voteType);

  List<Count> details(Long voteId);

//  Count turnOut(Long electionId ,Long voteId);
}
