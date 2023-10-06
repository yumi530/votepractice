package com.project.voting.exception.election;

import lombok.Getter;

@Getter
public class ElectionCustomException extends RuntimeException {

  private final ElectionErrorCode electionErrorCode;

  public ElectionCustomException(ElectionErrorCode electionErrorCode) {

    super(electionErrorCode.getDescription());
    this.electionErrorCode = electionErrorCode;

  }

}
