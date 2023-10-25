package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.candcount.CandCountDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChoiceCandCountService extends CommonCandCountService {

  private final VoteBoxRepository voteBoxRepository;
  private final CandCountRepository candCountRepository;

  @Override
  public boolean isValidCandCount(Long electionId, Long voteId, VoteType voteType) {
    return super.isValidCandCount(electionId, voteId, voteType);
  }

  @Override
  public CandCount countVotesResult(Long voteId, Long electionId, VoteType voteType) {

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


      return new CandCount();
    }

    private int getVoteTypeValue (VoteBox voteBox, VoteType voteType){
      switch (voteType) {
        case CHOICE:
          return voteBox.getChoices();
        case SCORE:
          return voteBox.getScores();
        case PREFERENCE:
          return voteBox.getRanks();
        default:
          return 0; // Handle unsupported vote types
      }
    }


  @Override
  public VoteType getVoteType() {
    return VoteType.CHOICE;
  }

  @Override
  public int extractField(VoteBox voteBox) {
    return voteBox.getChoices();
  }

  public CandCount createCandCount(CandCountDto candCountDto) {
    CandCount candCount = CandCount.builder()
      .electionId(candCountDto.getElectionId())
      .voteId(candCountDto.getVoteId())
      .candidateId(candCountDto.getCandidateId())
      .build();
    return candCount;
  }

}
