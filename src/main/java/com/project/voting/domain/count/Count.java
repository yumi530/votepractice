package com.project.voting.domain.count;

import com.project.voting.domain.vote.Vote;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Count {
  @Id
  @GeneratedValue
  private Long countId;
  private boolean isAgreed;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vote_vote_id")
  private Vote vote;

  @Builder
  public Count(Long countId, boolean isAgreed, Vote vote){
    this.countId = countId;
    this.isAgreed = isAgreed;
    this.vote = vote;

  }


}
