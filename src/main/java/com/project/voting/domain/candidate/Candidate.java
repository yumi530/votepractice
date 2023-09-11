package com.project.voting.domain.candidate;

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
@IdClass(CandId.class)
public class Candidate {

  @Id
  private Long electionId;

  @Id
  private Long voteId;

  @Id
  private Long candidateId;

  private String candidateName;
  private String candidateInfo;

  @Builder
  public Candidate(Long electionId, Long voteId, Long candidateId, String candidateName, String candidateInfo) {
    this.electionId = electionId;
    this.voteId = voteId;
    this.candidateId = candidateId;
    this.candidateName = candidateName;
    this.candidateInfo = candidateInfo;
  }

}
