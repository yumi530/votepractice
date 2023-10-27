package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChoiceCandCountService extends CandCountService {
  @Autowired
  VoteBoxRepository voteBoxRepository;
  @Autowired
  CandCountRepository candCountRepository;
  @Override
  public void countVotesResult(Long electionId, Long voteId) {
    List<VoteBox> voteBoxes = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);
    for (VoteBox voteBox : voteBoxes) {

      int sumValue;

      sumValue = calculateSumValue(voteBox.getCandidateId());
      int usersNum = calculateUsersNum(voteBox.getCandidateId());

      double avg = sumValue / usersNum;

      CandCount candCount = createCandCount(voteId, electionId);
      candCount.setCandidateId(voteBox.getCandidateId());
      candCount.setChoicesAvg(avg);
      candCountRepository.save(candCount);
    }
  }
  @Override
  public VoteType getVoteType() {
    return VoteType.CHOICE;
  }
  private int calculateSumValue(Long candidateId) {
    List<VoteBox> voteBoxes = voteBoxRepository.findAllByCandidateId(candidateId);

    int sum = 0;
    for (VoteBox voteBox : voteBoxes) {
      sum += voteBox.getChoices();
    }
    return sum;
  }
}
