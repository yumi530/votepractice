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
public class ScoreCandCountService extends CandCountService {

  @Autowired
  VoteBoxRepository voteBoxRepository;
  @Autowired
  CandCountRepository candCountRepository;

  @Override
  public void countVotesResult(Long electionId, Long voteId) {

    List<VoteBox> voteBoxes = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);

    for (VoteBox voteBox : voteBoxes) {

      int sumValue;
      double avg;

      sumValue = calculateSumValue(voteBox.getCandidateId());
      double usersNum = calculateUsersNum(voteBox.getCandidateId());

      avg = sumValue / usersNum;

      CandCount candCount = createCandCount(voteId, electionId);
      candCount.setCandidateId(voteBox.getCandidateId());
      candCount.setScoresAvg(avg);
      candCountRepository.save(candCount);
    }
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.SCORE;
  }

  private int calculateSumValue(Long candidateId) {
    List<VoteBox> voteBoxes = voteBoxRepository.findAllByCandidateId(candidateId);

    int sum = 0;
    for (VoteBox voteBox : voteBoxes) {
      sum += voteBox.getScores();
    }
    return sum;
  }
}

