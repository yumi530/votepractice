package com.project.voting.domain.cand_count;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandCountRepository extends JpaRepository<CandCount, Long> {

  List<CandCount> findByResult(boolean b);

  List<CandCount> findTotalRankByCandidateId(Long candidateId);

  List<CandCount> findAllCandidateIdsByVoteId(Long voteId);

}
