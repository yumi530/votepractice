package com.project.voting.domain.count;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountRepository extends JpaRepository<Count, Long> {

  List<Count> findAllCandidateIdsByVoteId(Long voteId);
}
