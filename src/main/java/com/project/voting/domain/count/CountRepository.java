package com.project.voting.domain.count;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountRepository extends JpaRepository<Count, Long> {


  Optional<Count> findByElectionIdAndVoteIdAndCandidateId(Long electionId, Long voteId, Long candidateId);
}
