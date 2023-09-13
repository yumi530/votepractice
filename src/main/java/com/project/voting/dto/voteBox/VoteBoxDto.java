package com.project.voting.dto.voteBox;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteBoxDto {

  private boolean hadChosen;
  private Long voteId;
  private String usersPhone;
  private boolean hadVoted;
  private Long electionId;
  private Long candidateId;
  private List<Long> candidateIds;

}
