package com.project.voting.domain.voteBox;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UsersVoteBoxKey implements Serializable {

  private Long electionId;
  private Long voteId;
  private Long candidateId;
  private String usersPhone;

}
