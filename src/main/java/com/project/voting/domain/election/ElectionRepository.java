package com.project.voting.domain.election;

import com.project.voting.domain.users.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

  Election findElectionIdByVotes_VoteId(Long voteId);

  Election findUsers_UsersPhoneByElectionId(Long electionId);

//  List<Election> findAllByUsersPhone(Users usersPhones);



}
