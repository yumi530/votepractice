package com.project.voting.domain.count;

import com.project.voting.domain.candidate.CandIdKey;
import com.project.voting.domain.vote.Vote;

import com.project.voting.domain.vote.VoteType;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(CountKey.class)
public class Count {


  @Id
  private Long electionId;
  @Id
  private Long voteId;
  @Id
  private Long candidateId;
  @Id
  private Long electedId;

  private boolean finalResult;

  private int totalRank;


  @Builder
  public Count(Long electionId, Long voteId, Long candidateId, boolean finalResult,
    int totalRank) {
    this.electionId = electionId;
    this.voteId = voteId;
    this.candidateId = candidateId;
    this.finalResult = finalResult;
    this.totalRank = totalRank;

  }


}
