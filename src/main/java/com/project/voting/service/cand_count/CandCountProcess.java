package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.cand_count.CandCountErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandCountProcess {

  private final VoteBoxRepository voteBoxRepository;
  private final CandCountRepository candCountRepository;

  public void processProsConsCandCount(Long electionId, Long voteId, List<VoteBox> voteBoxes) {
    // PROS_CONS 투표 처리
    Long countPros = voteBoxRepository.countByHadChosenTrueAndVoteId(voteId);
    Long countCons = voteBoxRepository.countByHadChosenFalseAndVoteId(voteId);
    Long prosRatio = countPros / (countPros + countCons) * 100;
    Long consRatio = countCons / (countPros + countCons) * 100;

    CandCount candCount = new CandCount();
    candCount.setElectionId(electionId);
    candCount.setVoteId(voteId);
    candCount.setCandidateId(voteBoxes.get(0).getCandidateId());

    candCount.setResult(countPros > countCons ? true : false);
    candCount.setProsRatio(prosRatio);
    candCount.setConsRatio(consRatio);

    candCountRepository.save(candCount);
  }

  public void processCandCount(VoteType voteType, Long electionId, Long voteId,
    List<VoteBox> voteBoxes, List<Double> avgList) {
    for (VoteBox voteBox : voteBoxes) {
      int sumValue = 0;
      double avg = 0.0;

      switch (voteType) {
        case CHOICE:
          sumValue = voteBoxRepository.sumChoicesByCandidateId(voteBox.getCandidateId());
          break;
        case SCORE:
          sumValue = voteBoxRepository.sumScoresByCandidateId(voteBox.getCandidateId());
          break;
        case PREFERENCE:
          sumValue = voteBoxRepository.sumRanksByCandidateId(voteBox.getCandidateId());
          break;
        default:
          throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_NOT_VALID);
      }

      Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(voteBox.getCandidateId());

      if (usersPhones > 0) {
        avg = (double) sumValue / (double) usersPhones;
      }

      avgList.add(avg);

      CandCount candCount = new CandCount();
      candCount.setElectionId(electionId);
      candCount.setVoteId(voteId);
      candCount.setCandidateId(voteBox.getCandidateId());

      switch (voteType) {
        case CHOICE:
          candCount.setChoicesAvg(avg);
          break;
        case SCORE:
          candCount.setScoresAvg(avg);
          break;
        case PREFERENCE:
          candCount.setRanksAvg(avg);
          break;
        default:
          throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_NOT_VALID);
      }

      candCountRepository.save(candCount);
    }
  }

}
