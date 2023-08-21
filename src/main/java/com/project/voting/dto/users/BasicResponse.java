package com.project.voting.dto.users;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class BasicResponse {
  private String message;
  private boolean success;

  @Builder
  public BasicResponse(String message, boolean success){
    this.message = message;
    this.success = success;
  }

}
