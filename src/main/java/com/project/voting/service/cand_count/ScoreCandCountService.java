package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreCandCountService extends CandCountService {

  private final VoteBoxRepository voteBoxRepository;
  private final CandCountRepository candCountRepository;


  @Override
  public void countVotesResult(Long voteId, Long electionId, VoteType voteType) {

    List<VoteBox> voteBoxes = voteBoxRepository.findAllByVoteId(voteId);
    List<Double> avgList = new ArrayList<>();

    for (VoteBox voteBox : voteBoxes) {
      int sumValue = 0;
      double avg = 0.0;

      sumValue = voteBoxRepository.sumChoicesByCandidateId(voteBox.getCandidateId());
      Double usersNum = voteBoxRepository.countUsersPhonesByCandidateId(voteBox.getCandidateId());

      if (usersNum > 0) {
        avg = sumValue / usersNum;
      }

      avgList.add(avg);

      CandCount candCount = CandCount.builder()
              .electionId(electionId)
              .voteId(voteId)
              .candidateId(voteBox.getCandidateId())
              .choicesAvg(avg)
              .build();
      candCountRepository.save(candCount);
    }
  }

//    List<VoteBox> voteBoxes = voteBoxRepository.findAllByVoteId(voteId);
//
//    for (VoteBox voteBox : voteBoxes) {
//      int sumValue = 0;
//
//      sumValue += extractField(voteBox);
//
//      sumValue / users
//
//      CandCount candCount = CandCount.builder()
//        .electionId(electionId)
//        .voteId(voteId)
//        .candidateId(voteBox.getCandidateId())
//        .scoresAvg(scoresAvg)
//        .build();
//
//      candCountRepository.save(candCount);
//    }
//    return new CandCount();
//  }

  @Override
  public VoteType getVoteType() {
    return VoteType.SCORE;
  }
}

