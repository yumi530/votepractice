package com.project.voting.domain.voteBox;

import com.project.voting.domain.vote.Vote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteBoxRepository extends JpaRepository<VoteBox, Long> {

  List<VoteBox> findAllByVoteId(Long voteId);

  VoteBox findByCandidateId(Long candidateId);


  Optional<VoteBox> findByCandidateIdAndUsersPhone(Long candidateId, String usersPhone);


  Long countAllByVoteId(Long voteId);

  Long countByIsAgreedTrueAndCandidateId(Long candidateId);

  Long countByIsAgreedFalseAndCandidateId(Long candidateId);

  Integer sumChoicesByCandidateId(Long candidateId);

  Integer sumScoresByCandidateId(Long candidateId);

  Integer sumRanksByCandidateId(Long candidateId);
}
