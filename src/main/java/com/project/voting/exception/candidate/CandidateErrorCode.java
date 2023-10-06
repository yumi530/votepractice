package com.project.voting.exception.candidate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CandidateErrorCode {

  NUMBERS_OF_CANDIDATE_NOT_VALID("후보자를 추가로 등록해야 합니다.");

  private final String description;
}
