package com.project.voting.exception.cand_count;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CandCountErrorCode {

  CAND_COUNT_TIME_NOT_VALID("선거 종료일 전에는 개표할 수 없습니다."),
  CAND_COUNT_NOT_VALID("개표가 불가능 합니다.");

  private final String description;
}
