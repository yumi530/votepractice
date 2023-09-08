package com.project.voting.domain.vote;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VoteId implements Serializable {

  private Long electionId;
  private Long voteId;

}
