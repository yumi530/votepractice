package com.project.voting.domain.vote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.election.Election;
import com.project.voting.dto.vote.VoteDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Vote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long voteId;
  private String voteTitle;
  private String candidateName;
  private String candidateInfo;
  private boolean result;
  private double prosRatio;
  private double consRatio;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "election_election_id")
  private Election election;

  @JsonIgnore
  @OneToMany(mappedBy = "vote")
  private List<Count> counts = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private VoteType voteType;


  @Builder
  public Vote(Long voteId, String voteTitle, String candidateName,
    String candidateInfo, Election election, List<Count> counts, boolean result, double prosRatio,
    double consRatio, VoteType voteType) {
    this.voteId = voteId;
    this.voteTitle = voteTitle;
    this.candidateName = candidateName;
    this.candidateInfo = candidateInfo;
    this.election = election;
    this.counts = counts;
    this.result = result;
    this.prosRatio = prosRatio;
    this.consRatio = consRatio;
    this.voteType = voteType;
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

//    public static Vote toEntity(VoteDto voteDto) {
//        return Vote.builder()
//                .voteId(voteDto.getVoteId())
//                .voteTitle(voteDto.getVoteTitle())
//                .voteType(voteDto.getVoteType())
//                .candidateName(voteDto.getCandidateName())
//                .candidateInfo(voteDto.getCandidateInfo())
//                .agreeYn(voteDto.isAgreeYn())
//                .build();
//        if (voteDto.getElectionId() != null) {
//            Election election = new Election();
//            election.setId(voteDto.getElection().getElectionId());
//            vote.setElection(election);
//        }
//        return vote;
//    }
//    public Vote(int voteId, String voteTitle, int voteType, String candidateName, String candidateInfo, int preference){
//        this.voteId = voteId;
//        this.voteTitle = voteTitle;
//        this.voteType = voteType;
//        this.candidateName = candidateName;
//        this.candidateInfo = candidateInfo;
//        this.preference = preference;
//    }

