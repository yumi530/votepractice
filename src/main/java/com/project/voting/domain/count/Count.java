package com.project.voting.domain.count;

import com.project.voting.domain.vote.Vote;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Count {
  @Id
  @GeneratedValue
  private Long countId;
  private boolean isAgreed;
  private boolean hadVoted;
//  private boolean result;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vote_vote_id",referencedColumnName = "voteId")
  private Vote vote;

  @Builder
  public Count(Long countId, boolean isAgreed, Vote vote, boolean hadVoted){
    this.countId = countId;
    this.isAgreed = isAgreed;
    this.vote = vote;
    this.hadVoted = hadVoted;



  }


}
