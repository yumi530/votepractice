package com.project.voting.domain.candidate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

  List<Candidate> findAllByVoteId(Long voteId);

  Candidate findByVoteId(Long voteId);


}
