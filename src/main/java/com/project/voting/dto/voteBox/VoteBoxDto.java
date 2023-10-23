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
  private Long electionId;
  private Long voteId;
  private VoteType voteType;
  private String usersPhone;
  private boolean hadChosen;
  private Long candidateId;
  private List<Long> candidateIds;
  private Long chosenCandidateId; // 선택 투표
  private List<Integer> scores; // 점수 투표
  private List<Integer> ranks; // 선호도 투표
  private List<VoteBox> detailVoteBox;

//    private Integer choices;
//    private boolean hadVoted;
//    private Integer scores;
//    private Integer ranks;
//    private List<Integer> scoreList = new ArrayList<>();
//    private List<Integer> rankList = new ArrayList<>();

//  public void addScore(Integer scores) {
//    this.scoreList.add(scores);
//  }

//  public void addRank(Integer ranks) {
//    this.rankList.add(ranks);
//  }

}
