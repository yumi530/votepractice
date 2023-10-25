package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.vote.VoteType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainCandCountService {

  private final CandCountFactory candCountFactory;
  private final CandCountRepository candCountRepository;

  public void countVotesResult(Long voteId, Long electionId, VoteType voteType){
    candCountFactory.getService(voteType).countVotesResult(voteId, electionId, voteType);
  }

  public List<CandCount> details(Long voteId) {
    return candCountRepository.findAllCandidateIdsByVoteId(voteId);
  }

}
