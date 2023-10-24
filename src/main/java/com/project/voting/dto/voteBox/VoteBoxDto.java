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
  private Long candidateId;
  private List<Long> candidateIds;
  private List<VoteBox> detailVoteBox;
  private Long chosenCandidateId; // 선택 투표
  private List<Integer> scores; // 점수 투표
  private List<Integer> ranks; // 선호도 투표
  private boolean hadChosen;


}
