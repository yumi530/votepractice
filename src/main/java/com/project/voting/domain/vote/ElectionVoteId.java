package com.project.voting.domain.vote;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
public class ElectionVoteId implements Serializable {

  private Long electionId;
  private Long voteId;

}
