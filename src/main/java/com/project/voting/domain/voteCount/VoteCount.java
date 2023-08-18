package com.project.voting.domain.voteCount;

import com.project.voting.domain.vote.Vote;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteCount {
  @Id
  @GeneratedValue
  private Long voteCountId;
  private boolean isAgreed;

  @OneToOne(fetch = FetchType.LAZY)
  private Vote vote;

  @Builder
  public VoteCount(Long voteCountId, boolean isAgreed, Vote vote){
    this.voteCountId = voteCountId;
    this.isAgreed = isAgreed;
    this.vote = vote;
  }


}
