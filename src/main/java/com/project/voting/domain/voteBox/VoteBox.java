package com.project.voting.domain.voteBox;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

  private boolean isAgreed;

  private Integer scores;

  private Integer ranks;

  private String choices;

  private boolean hadVoted;

  private boolean electionCompleted;


  @Builder
  public VoteBox(Long electionId, Long voteId, Long candidateId, String usersPhone, boolean isAgreed, Integer scores, Integer ranks, String choices,boolean hadVoted, boolean electionCompleted) {
    this.electionId = electionId;
    this.voteId = voteId;
    this.candidateId = candidateId;
    this.usersPhone = usersPhone;
    this.isAgreed = isAgreed;
    this.scores = scores;
    this.ranks = ranks;
    this.choices = choices;
    this.hadVoted = hadVoted;
    this.electionCompleted = electionCompleted;
  }


}
