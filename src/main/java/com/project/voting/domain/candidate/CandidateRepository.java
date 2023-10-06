package com.project.voting.domain.candidate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

  List<Candidate> findAllByVoteId(Long voteId);


  List<Candidate> findAllCandidateIdByVoteId(Long voteId);


  List<Candidate> findAllByCandidateId(Long candidateId);

  Candidate countCandidateIdByVoteId(Long voteId);

  List<Candidate> findAllCandidateIdsByVoteId(Long voteId);

}
