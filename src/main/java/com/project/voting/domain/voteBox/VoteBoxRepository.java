package com.project.voting.domain.voteBox;

import com.project.voting.domain.vote.Vote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteBoxRepository extends JpaRepository<VoteBox, Long> {

  List<VoteBox> findAllByVoteId(Long voteId);

  List<VoteBox> findAllScoresByCandidateId(Long candidateId);

  Optional<VoteBox> findByCandidateId(Long candidateId);


}
