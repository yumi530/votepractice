package com.project.voting.domain.count;

import com.project.voting.domain.candidate.CandIdKey;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(CandIdKey.class)
public class Count {

  @Id
  private Long electionId;
  @Id
  private Long voteId;
  @Id
  private Long candidateId;

  private boolean electedYn;

  private int totalRank;


  @Builder
  public Count(Long electionId, Long voteId, Long candidateId, boolean electedYn,
    int totalRank) {
    this.electionId = electionId;
    this.voteId = voteId;
    this.candidateId = candidateId;
    this.electedYn = electedYn;
    this.totalRank = totalRank;

  }


}
