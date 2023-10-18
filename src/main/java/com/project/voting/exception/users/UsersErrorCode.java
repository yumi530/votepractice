package com.project.voting.exception.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UsersErrorCode {

  USERS_LIST_NOT_FOUND("선거인 명부에 존재하지 않습니다."),
  NOT_LOGGED_IN("로그인을 해주십시오."),
  USERS_FILE_NOT_DELETED("명부 삭제가 불가 합니다.");

  private final String description;

}
