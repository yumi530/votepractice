package com.project.voting.exception.candidate;

import lombok.Getter;

@Getter
public class CandidateCustomException extends RuntimeException {

  private final CandidateErrorCode candidateErrorCode;

  public CandidateCustomException(CandidateErrorCode candidateErrorCode) {

    super(candidateErrorCode.getDescription());
    this.candidateErrorCode = candidateErrorCode;
  }

}
