package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.candcount.CandCountDto;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.cand_count.CandCountErrorCode;
import com.project.voting.exception.election.ElectionCustomException;
import com.project.voting.exception.election.ElectionErrorCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChoiceCandCountService implements CandCountService {

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
    List<Double> avgList = new ArrayList<>();

    for (VoteBox voteBox : voteBoxes) {
      int sumValue = 0;
//      sumValue += voteBox.getChoices();
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
  @Override
  public VoteType getVoteType() {
    return VoteType.CHOICE;
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
