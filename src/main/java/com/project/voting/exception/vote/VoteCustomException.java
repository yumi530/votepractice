package com.project.voting.exception.vote;

import lombok.Getter;

@Getter
public class VoteCustomException extends RuntimeException {

  private final VoteErrorCode voteErrorCode;

  public VoteCustomException(VoteErrorCode voteErrorCode) {

    super(voteErrorCode.getDescription());
    this.voteErrorCode = voteErrorCode;

  }

}
