package com.project.voting.service.users;

import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.dto.users.UsersDto;
import com.project.voting.exception.users.UsersCustomException;
import com.project.voting.exception.users.UsersErrorCode;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;

  @Override
  public List<UsersDto> detailList(String usersPhone) {
    List<Users> usersList = usersRepository.findAllByUsersPhone(usersPhone);
    if (usersList.isEmpty()) {
      throw new UsersCustomException(UsersErrorCode.NOT_LOGGED_IN);
    }
    return UsersDto.of(usersList);
  }

}






