package com.project.voting.dto.count;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountDto {

  private Long countId;
  private boolean isAgreed;
  private Long voteId;
  private boolean hadVoted;

}