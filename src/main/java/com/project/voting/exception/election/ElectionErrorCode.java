package com.project.voting.exception.election;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElectionErrorCode {

  START_TIME_NOT_VALID("시작일시는 현재 시각 이후로 설정해야 합니다."),
  END_TIME_NOT_VALID("종료일시는 시작일시 이후로 설정해야 합니다."),
  ELECTION_NOT_AVAILABLE("투표 가능 시간이 아닙니다.");

  private final String description;
}
