package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.cand_count.CandCountErrorCode;
import com.project.voting.exception.election.ElectionCustomException;
import com.project.voting.exception.election.ElectionErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProsConsCandCountService implements CandCountService{

  private final ElectionRepository electionRepository;
  private final VoteBoxRepository voteBoxRepository;
  private final CandCountRepository candCountRepository;

  @Override
  public CandCount countVotesResult(Long voteId, Long electionId, VoteType voteType) {

    Optional<Election> optionalElection = this.electionRepository.findById(electionId);
    Election election = optionalElection.orElseThrow(() -> new ElectionCustomException(ElectionErrorCode.ELECTION_NOT_GENERATED));

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
    }

    List<VoteBox> voteBoxes = voteBoxRepository.findAllByVoteId(voteId);

//    Long countPros = voteBoxRepository.countByHadChosenTrueAndVoteId(voteId);
//    Long countCons = voteBoxRepository.countByHadChosenFalseAndVoteId(voteId);
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
      .result(countPros > countCons)
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
}
