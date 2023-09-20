package com.project.voting.domain.voteBox;

import com.project.voting.domain.candidate.Candidate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteBoxRepository extends JpaRepository<VoteBox, Long> {

  List<VoteBox> findAllByVoteId(Long voteId);

  List<VoteBox> findAllScoresByCandidateId(Long candidateId);

  VoteBox findByCandidateId(Long candidateId);
}
