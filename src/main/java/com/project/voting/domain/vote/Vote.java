package com.project.voting.domain.vote;

import com.project.voting.domain.election.Election;
import com.project.voting.dto.vote.VoteDto;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Vote {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int voteId;
  private String voteTitle;
  private int voteType;
  private String candidateName;
  private String candidateInfo;
  private boolean agreeYn;
  private int preference;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "election_id")
  private Election election;

  @Builder
  public Vote(int voteId, String voteTitle, int voteType, String candidateName,
    String candidateInfo, boolean agreeYn, int preference, Election election) {
    this.voteId = voteId;
    this.voteTitle = voteTitle;
    this.voteType = voteType;
    this.candidateName = candidateName;
    this.candidateInfo = candidateInfo;
    this.agreeYn = agreeYn;
    this.preference = preference;
    this.election = election;
  }

  public static Vote toEntity(VoteDto voteDto) {
    return Vote.builder()
      .voteId(voteDto.getVoteId())
      .voteTitle(voteDto.getVoteTitle())
      .voteType(voteDto.getVoteType())
      .candidateName(voteDto.getCandidateName())
      .candidateInfo(voteDto.getCandidateInfo())
      .agreeYn(voteDto.isAgreeYn())
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

