package com.project.voting.exception.cand_count;

import lombok.Getter;


@Getter
public class CandCountCustomException extends RuntimeException {

  private final CandCountErrorCode candCountErrorCode;

  public CandCountCustomException(CandCountErrorCode candCountErrorCode) {

    super(candCountErrorCode.getDescription());
    this.candCountErrorCode = candCountErrorCode;
  }

}
