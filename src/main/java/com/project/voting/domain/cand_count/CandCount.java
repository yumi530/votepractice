package com.project.voting.domain.cand_count;

import com.project.voting.domain.candidate.CandIdKey;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
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

}
