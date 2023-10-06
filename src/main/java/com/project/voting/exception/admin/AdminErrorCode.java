package com.project.voting.exception.admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminErrorCode {

  ADMIN_NOT_FOUND("관리자가 존재하지 않습니다.");

  private final String description;

}
