package com.project.voting.service.cand_count;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.cand_count.CandCountErrorCode;
import com.project.voting.exception.election.ElectionCustomException;
import com.project.voting.exception.election.ElectionErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class CommonCandCountService implements CandCountService{

  @Autowired
  private ElectionRepository electionRepository;

  protected boolean isValidCandCount(Long electionId, Long voteId, VoteType voteType){

    Optional<Election> optionalElection = this.electionRepository.findById(electionId);
    Election election = optionalElection.orElseThrow(
      () -> new ElectionCustomException(ElectionErrorCode.ELECTION_NOT_GENERATED));

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
    }
    return true;
  }

//  protected double countUsersNum (List<VoteBox> voteBoxes) {
//    Set<String> uniqueUserPhones = voteBoxes.stream()
//      .map(VoteBox::getUsersPhone)
//      .collect(Collectors.toSet());
//
//    return uniqueUserPhones.size();
//  }

  protected long calculateUsersNum(Long candidateId, List<VoteBox> voteBoxes) {
    return voteBoxes.stream()
      .filter(vb -> vb.getCandidateId().equals(candidateId))
      .map(VoteBox::getUsersPhone)
      .distinct()
      .count();
  }

}
