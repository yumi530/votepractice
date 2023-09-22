package com.project.voting.domain.count;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CountKey implements Serializable {

  private Long electedId;
  private Long electionId;
  private Long voteId;
  private Long candidateId;
}
