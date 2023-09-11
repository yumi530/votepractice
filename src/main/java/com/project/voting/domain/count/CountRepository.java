package com.project.voting.domain.count;

import com.project.voting.domain.vote.VoteType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountRepository extends JpaRepository<Count, Long> {

//  @Query("SELECT SUM(c.scores) FROM Count c WHERE c.vote.voteId = :voteId AND c.voteType = :voteType AND c.candidateName = :candidateName")
//  Long sumScoresByVoteTypeAndVoteVoteIdAndCandidateName(@Param("voteId") Long voteId,
//    @Param("voteType") VoteType voteType, @Param("candidateName") String candidateName);
//
//  Long countByIsAgreedTrueAndVoteVoteId(Long voteId);
//
//  Optional<Count> findByHadVoted(Long countId);
//
//  Long countByIsAgreedFalseAndVoteVoteId(Long voteId);
//
//  Long countAllByVoteVoteId(Long voteId);
//
//  Long countByVoteTypeAndIsAgreedFalseAndVoteVoteId(VoteType voteType, Long voteId);
//
//  Long countByVoteTypeAndIsAgreedTrueAndVoteVoteId(VoteType voteType, Long voteId);
}
