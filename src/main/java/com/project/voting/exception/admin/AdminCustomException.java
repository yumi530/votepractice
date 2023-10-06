package com.project.voting.exception.admin;

import lombok.Getter;

@Getter
public class AdminCustomException extends RuntimeException {

  private final AdminErrorCode adminErrorCode;

  public AdminCustomException(AdminErrorCode adminErrorCode) {
    super(adminErrorCode.getDescription());
    this.adminErrorCode = adminErrorCode;
  }

}
