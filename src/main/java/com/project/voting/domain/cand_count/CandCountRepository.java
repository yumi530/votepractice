package com.project.voting.domain.cand_count;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandCountRepository extends JpaRepository<CandCount, Long> {

  List<CandCount> findAllCandidateIdsByVoteId(Long voteId);

  CandCount findCandidateIdByVoteId(Long voteId);

  List<CandCount> findAllByResultAndVoteId(boolean b, Long voteId);
}
