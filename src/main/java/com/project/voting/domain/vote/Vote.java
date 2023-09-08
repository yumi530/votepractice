package com.project.voting.domain.vote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.dto.vote.VoteDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(VoteId.class)
public class Vote {

  @Id
  private Long electionId;
  @Id
  private Long voteId;

  private String voteTitle;
  private String candidateName;
  private String candidateInfo;
  private boolean result;
  private double prosRatio;
  private double consRatio;
  private double scores;
  private int ranks;

//  @JsonIgnore
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "election_election_id")
//  private Election election;

//  @JsonIgnore
//  @OneToMany(mappedBy = "vote")
//  private List<Count> counts = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private VoteType voteType;


  @Builder
  public Vote(Long voteId, String voteTitle, String candidateName,
    String candidateInfo, Election election, List<Count> counts, boolean result, double prosRatio,
    double consRatio, VoteType voteType, double scores, int ranks) {
    this.voteId = voteId;
    this.voteTitle = voteTitle;
    this.candidateName = candidateName;
    this.candidateInfo = candidateInfo;
//    this.election = election;
//    this.counts = counts;
    this.result = result;
    this.prosRatio = prosRatio;
    this.consRatio = consRatio;
    this.voteType = voteType;
    this.scores = scores;
    this.ranks = ranks;
  }

  public static Vote toEntity(VoteDto voteDto) {
    return Vote.builder()
      .voteId(voteDto.getVoteId())
      .voteTitle(voteDto.getVoteTitle())
      .candidateName(voteDto.getCandidateName())
      .candidateInfo(voteDto.getCandidateInfo())
      .voteType(VoteType.valueOf(voteDto.getVoteType()))
      .build();
  }
}


