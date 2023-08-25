package com.project.voting.service.users;

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
    if (usersList.isEmpty()){
      throw new RuntimeException("로그인을 해주십시오.");
    }
    return UsersDto.of(usersList);
  }

//  @Override
//  public List<UsersDto> detailList(Long electionId) {
//    List<Users> usersList = usersRepository.findAllByElection_ElectionId(electionId);
//    return UsersDto.of(usersList);
//  }



}

