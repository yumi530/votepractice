package com.project.voting.domain.vote;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

//  List<Vote> findAllById(List<Long> voteId);
}


