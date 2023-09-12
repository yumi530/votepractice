package com.project.voting.domain.vote;

import com.project.voting.dto.vote.VoteDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(ElectionVoteIdKey.class)
public class Vote {

  @Id
  private Long electionId;
  @Id
  private Long voteId;

  private String voteTitle;

  @Enumerated(EnumType.STRING)
  private VoteType voteType;

  @Builder
  public Vote(Long voteId, String voteTitle, VoteType voteType, Long electionId) {
    this.voteTitle = voteTitle;
    this.voteType = voteType;
    this.electionId = electionId;
    this.voteId = voteId;
  }

  public static Vote toEntity(VoteDto voteDto) {
    Long voteId = voteDto.getElectionVoteId();
    Long electionId = voteDto.getElectionId();
    return Vote.builder()
      .voteId(voteId)
      .electionId(electionId)
      .voteTitle(voteDto.getVoteTitle())
      .voteType(VoteType.valueOf(voteDto.getVoteType()))
      .build();
  }
}


