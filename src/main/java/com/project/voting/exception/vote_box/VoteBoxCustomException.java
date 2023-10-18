package com.project.voting.exception.vote_box;

import lombok.Getter;

@Getter
public class VoteBoxCustomException extends RuntimeException {

  private final VoteBoxErrorCode voteBoxErrorCode;

  public VoteBoxCustomException(VoteBoxErrorCode voteBoxErrorCode) {

    super(voteBoxErrorCode.getDescription());
    this.voteBoxErrorCode = voteBoxErrorCode;

  }

}
