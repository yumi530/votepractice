package com.project.voting.service.users;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.dto.users.UsersDto;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;

  @Override
  public List<UsersDto> detailList(String usersPhone) {
    List<Users> usersList = usersRepository.findAllById(Collections.singleton(usersPhone));
    if (usersList.isEmpty()) {
      throw new RuntimeException("로그인을 해주십시오.");
    }
    return UsersDto.of(usersList);
  }

//  @Override
//  public List<UsersDto> detailList(Long electionId) {
//    List<Users> usersList = usersRepository.findAllByElection_ElectionId(electionId);
//    return UsersDto.of(usersList);
//  }

//  @Override
//  public Count save(boolean isAgreed, Long voteId) {
//    Vote vote = voteRepository.findById(voteId).orElse(null);
//    if (vote == null) {
//      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
//    }
//    Count count = toCount(isAgreed, vote);
//    return countRepository.save(count);
//  }

//  @Override
//  public Users complete(String usersPhone,  boolean usersCompleted) {
//    Users completeUsers = usersRepository.findById(usersPhone).orElse(null);
//    if (completeUsers == null) {
//      throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
//    }
//    Users users = toUsers(usersPhone, usersCompleted);
////    completeUsers.setUsersCompleted(true);
//    return usersRepository.save(completeUsers);
//  }
//
//  private Users toUsers(String usersPhone,boolean usersCompleted) {
//    return Users.builder()
//      .usersPhone(usersPhone)
//      .usersCompleted(true)
//      .build();
//  }
}






