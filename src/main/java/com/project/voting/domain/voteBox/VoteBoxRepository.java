package com.project.voting.domain.voteBox;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteBoxRepository extends JpaRepository<VoteBox, Long> {

  List<VoteBox> findAllByVoteId(Long voteId);

  List<VoteBox> findAllScoresByCandidateId(Long candidateId);
}
