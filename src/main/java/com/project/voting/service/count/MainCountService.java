package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.vote.VoteType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainCountService {

  private final CountFactory countFactory;
  private final CountRepository countRepository;

  public void votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {
   countFactory.getService(voteType).votesResultConfirm(electionId, voteId, voteType);
  }

  public List<Count> details(Long voteId) {
    return countRepository.findAllCandidateIdsByVoteId(voteId);
  }

}
