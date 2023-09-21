package com.project.voting.domain.voteBox;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteBoxRepository extends JpaRepository<VoteBox, Long> {

  List<VoteBox> findAllByVoteId(Long voteId);

  VoteBox findByCandidateId(Long candidateId);

  Optional<VoteBox> findByCandidateIdAndUsersPhone(Long candidateId, String usersPhone);

  Long countAllByVoteId(Long voteId);

  Long countByIsAgreedTrueAndCandidateId(Long candidateId);

  Long countByIsAgreedFalseAndCandidateId(Long candidateId);

  @Query(value = "SELECT SUM(CAST(vb.choices AS DECIMAL(10, 2))) FROM vote_box vb WHERE vb.candidate_id = :candidateId", nativeQuery = true)
  Integer sumChoicesByCandidateId(@Param("candidateId") Long candidateId);

  @Query("SELECT SUM(vb.scores) FROM VoteBox vb WHERE vb.candidateId = :candidateId")
  Integer sumScoresByCandidateId(@Param("candidateId") Long candidateId);

  @Query("SELECT SUM(vb.ranks) FROM VoteBox vb WHERE vb.candidateId = :candidateId")
  Integer sumRanksByCandidateId(@Param("candidateId") Long candidateId);

  VoteBox findCandidateIdByVoteId(Long voteId);

  List<VoteBox> findAllCandidateIdsByVoteId(Long voteId);
}
