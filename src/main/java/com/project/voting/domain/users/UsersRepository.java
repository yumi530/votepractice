package com.project.voting.domain.users;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
  List<Users> findAll();

  List<Users> findAllByUsersPhone(String usersPhone);

  Optional<Users> findByUsersPhone(String phoneNumber);

  Integer countUsersPhonesByElectionId(Long electionId);
}
