package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProsConsCandCountService extends CommonCandCountService{

  private final VoteBoxRepository voteBoxRepository;
  private final CandCountRepository candCountRepository;

  @Override
  public boolean isValidCandCount(Long electionId, Long voteId, VoteType voteType) {
    return super.isValidCandCount(electionId, voteId, voteType);
  }

  @Override
  public CandCount countVotesResult(Long voteId, Long electionId, VoteType voteType) {

    List<VoteBox> voteBoxes = voteBoxRepository.findAllByVoteId(voteId);

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

    CandCount candCount = CandCount.builder()
      .electionId(electionId)
      .voteId(voteId)
      .candidateId(voteBoxes.get(0).getCandidateId())
      .prosRatio(prosRatio)
      .consRatio(consRatio)
      .result(countPros > countCons) //값 수정해야 함
      .build();

    candCountRepository.save(candCount);
    return candCount;
  }

  private Double calculateProsRatio(Long countPros, Long countCons) {
    return (double) ((countPros * 100) / (countPros + countCons));
  }

  private Double calculateConsRatio(Long countPros, Long countCons) {
    return (double) ((countCons * 100) / (countPros + countCons));
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.PROS_CONS;
  }

  @Override
  public int extractField(VoteBox voteBox) {
    return 0;
  }
}
