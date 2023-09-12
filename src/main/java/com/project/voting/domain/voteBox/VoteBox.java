package com.project.voting.domain.voteBox;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@IdClass(UsersVoteBoxKey.class)
public class VoteBox {

  @Id
  private Long electionId;

  @Id
  private Long voteId;

  @Id
  private Long candidateId;

  @Id
  private String usersPhone;

  private boolean hadChosen;

  private int scores;

  private int ranks;

  private boolean hadVoted;

  private boolean electionCompleted;


}
