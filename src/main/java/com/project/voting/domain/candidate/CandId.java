package com.project.voting.domain.candidate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CandId implements Serializable {

  private Long electionId;
  private Long voteId;
  private Long candidateId;

}
