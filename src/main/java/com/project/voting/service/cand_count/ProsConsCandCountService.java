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
public class ProsConsCandCountService extends CandCountService {
  @Autowired
  VoteBoxRepository voteBoxRepository;
  @Autowired
  CandCountRepository candCountRepository;

  @Override
  public void countVotesResult(Long electionId, Long voteId) {

    List<VoteBox> voteBoxes = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);

    Long countPros = 0L;
    Long countCons = 0L;

    for(VoteBox voteBox : voteBoxes) {
      if (voteBox.isHadChosen()) {
        countPros++;
      } else {
        countCons++;
      }
    }

    Double prosRatio = calculateProsRatio(countPros, countCons);
    Double consRatio = calculateConsRatio(countPros, countCons);

    CandCount candCount = createCandCount(voteId, electionId);
    candCount.setCandidateId(voteBoxes.get(0).getCandidateId());
    candCount.setProsRatio(prosRatio);
    candCount.setConsRatio(consRatio);
    candCount.setResult(countPros > countCons);
    candCountRepository.save(candCount);
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.PROS_CONS;
  }

  private Double calculateProsRatio(Long countPros, Long countCons) {
    return (double) ((countPros * 100) / (countPros + countCons));
  }

  private Double calculateConsRatio(Long countPros, Long countCons) {
    return (double) ((countCons * 100) / (countPros + countCons));
  }
}
