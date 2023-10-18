package com.project.voting.exception.vote_box;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteBoxErrorCode {

  SCORE_NOT_VALID("점수가 0~100점 사이가 아닙니다."),
  PREFER_NOT_VALID("올바른 선호도 값을 입력해주세요."),
  VOTE_DUPLICATED("중복 투표 불가 !"),
  VOTE_NOT_FOUND("투표 정보를 찾을 수 없습니다.");

  private final String description;

}
