package com.project.voting.exception.users;

import lombok.Getter;

@Getter
public class UsersCustomException extends RuntimeException {

  private final UsersErrorCode usersErrorCode;

  public UsersCustomException(UsersErrorCode usersErrorCode) {

    super(usersErrorCode.getDescription());
    this.usersErrorCode = usersErrorCode;

  }

}
