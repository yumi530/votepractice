package com.project.voting.dto.election;

import com.project.voting.domain.election.Election;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ElectionDto {
  private Long electionId;
  private String electionTitle;
  private String groupName;
  private String electionStartDt;
  private String electionEndDt;

  @Builder
  public ElectionDto(Long electionId, String electionTitle, String groupName, String electionStartDt, String electionEndDt){
    this.electionId = electionId;
    this.electionTitle = electionTitle;
    this.groupName = groupName;
    this.electionStartDt = electionStartDt;
    this.electionEndDt = electionEndDt;
  }

  public Election toEntity() {
    return Election.builder()
            .electionId(electionId)
            .electionTitle(electionTitle)
            .electionStartDt(electionStartDt)
            .electionEndDt(electionEndDt)
            .build();
  }
}
