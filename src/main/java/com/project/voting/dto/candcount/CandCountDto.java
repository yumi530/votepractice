package com.project.voting.dto.candcount;

import javax.persistence.Id;
import lombok.Data;

@Data
public class CandCountDto {

  private Long electionId;

  private Long voteId;

  private Long candidateId;

  private double prosRatio;

  private double consRatio;

  private boolean result;

  private double scoresAvg;

  private double ranksAvg;

  private double choicesAvg;

}
