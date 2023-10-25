package com.project.voting.domain.vote;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum VoteType {
  PROS_CONS("찬반"),
  CHOICE("선택"),
  SCORE("점수"),
  PREFERENCE("선호도");

  private final String value;

  VoteType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }


}
