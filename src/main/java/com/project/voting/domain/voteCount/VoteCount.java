package com.project.voting.domain.voteCount;

import com.project.voting.domain.vote.Vote;

import javax.persistence.*;

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
  @JoinColumn(name = "vote_vote_id")
  private Vote vote;

  @Builder
  public VoteCount(Long voteCountId, boolean isAgreed, Vote vote){
    this.voteCountId = voteCountId;
    this.isAgreed = isAgreed;
    this.vote = vote;
  }


}
