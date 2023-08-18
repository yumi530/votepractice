package com.project.voting.domain.voteCount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteCountRepository extends JpaRepository<VoteCount, Long> {

}
