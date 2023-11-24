package com.project.voting.exception.vote;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCode {
  VOTE_TYPE_NOT_FOUND("유효하지 않은 투표 타입 입니다.");

  private final String description;

}
