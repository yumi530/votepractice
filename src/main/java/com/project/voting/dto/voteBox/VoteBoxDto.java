package com.project.voting.dto.voteBox;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteBoxDto {

  private boolean isAgreed;
  private Integer scores;
  private Integer ranks;
  private String choices;
  private Long voteId;
  private String usersPhone;
  private boolean hadVoted;
  private Long electionId;
  private Long candidateId;
  private List<Long> candidateIds;
  private VoteType voteType;
  private List<VoteBox> detailVoteBox;
  private List<Integer> scoreList = new ArrayList<>();
  private List<Integer> rankList = new ArrayList<>();


  public void addScore(Integer scores) {
    this.scoreList.add(scores);
  }

  public void addRank(Integer ranks) {
    this.rankList.add(ranks);
  }

}
