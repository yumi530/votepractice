package com.project.voting.domain.cand_count;

import com.project.voting.domain.candidate.CandIdKey;
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
@IdClass(CandIdKey.class)
public class CandCount {

  @Id
  private Long electionId;

  @Id
  private Long voteId;

  @Id
  private Long candidateId;

  private double prosRatio;

  private double consRatio;

  private boolean result;

  private double scoresAvg;

  private double ranksAvg;

  private double choicesAvg;

  private int totalRank;

  @Builder
  public CandCount(Long electionId, Long voteId, Long candidateId, double prosRatio, double consRatio, boolean result, double scoresAvg, double ranksAvg, double choicesAvg, int totalRank) {
    this.electionId = electionId;
    this.voteId = voteId;
    this.candidateId = candidateId;
    this.prosRatio = prosRatio;
    this.consRatio = consRatio;
    this.result = result;
    this.scoresAvg = scoresAvg;
    this.ranksAvg = ranksAvg;
    this.choicesAvg = choicesAvg;
    this.totalRank = totalRank;
  }

}
