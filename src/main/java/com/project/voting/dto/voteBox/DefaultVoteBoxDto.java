package com.project.voting.dto.voteBox;

import java.util.List;
import lombok.Data;

@Data
public class DefaultVoteBoxDto extends VoteBoxDto {

  private Long chosenCandidateId; // 선택 투표
  private List<Integer> scores; // 점수 투표
  private List<Integer> ranks; // 선호도 투표

}
