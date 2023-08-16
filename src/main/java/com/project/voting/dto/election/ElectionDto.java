package com.project.voting.dto.election;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ElectionDto {

  private Long electionId;
  private String electionTitle;
  private String groupName;
  private String electionStartDt;
  private String electionEndDt;
  private List<Vote> votes;

  @Builder
  public ElectionDto(Long electionId, String electionTitle, String groupName,
    String electionStartDt, String electionEndDt, List<Vote> votes) {
    this.electionId = electionId;
    this.electionTitle = electionTitle;
    this.groupName = groupName;
    this.electionStartDt = electionStartDt;
    this.electionEndDt = electionEndDt;
    this.votes = votes;
  }

  public Election toEntity() {
    return Election.builder()
      .electionId(electionId)
      .electionTitle(electionTitle)
      .electionStartDt(electionStartDt)
      .electionEndDt(electionEndDt)
      .votes(votes)
      .build();
  }
}
