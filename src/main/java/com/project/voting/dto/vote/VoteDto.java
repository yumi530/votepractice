package com.project.voting.dto.vote;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VoteDto {

    private int voteId;
    private String voteTitle;
    private int voteType;
    private String candidateName;
    private String candidateInfo;
    private boolean agreeYn;
    private int preference;
    private Election election;

    @Builder
    public VoteDto(int voteId, String voteTitle, int voteType, String candidateName, String candidateInfo, boolean agreeYn, int preference, Election election) {
        this.voteId = voteId;
        this.voteTitle = voteTitle;
        this.voteType = voteType;
        this.candidateName = candidateName;
        this.candidateInfo = candidateInfo;
        this.agreeYn = agreeYn;
        this.preference = preference;
        this.election = election;
    }

//public static VoteDto from(Vote vote) {
//    VoteDto dto = new VoteDto();
//    dto.setVoteId(vote.getVoteId());
//    dto.setElection(vote.getElection()); // 변경: Election 객체 설정
//    dto.setVoteTitle(vote.getVoteTitle());
//    dto.setVoteType(vote.getVoteType());
//    dto.setCandidateName(vote.getCandidateName());
//    dto.setCandidateInfo(vote.getCandidateInfo());
//    dto.setAgreeYn(vote.isAgreeYn());
//    return dto;
//}

//    // 변경: Election 객체 getter와 setter
//    public Election getElection() {
//        return election;
//    }
//
//    public void setElection(Election election) {
//        this.election = election;
//    }
//public static Vote toEntity(VoteDto voteDto) {
//    return Vote.builder()
//      .voteId(voteDto.getVoteId())
//      .voteTitle(voteDto.getVoteTitle())
//      .voteType(voteDto.getVoteType())
//      .candidateName(voteDto.getCandidateName())
//      .candidateInfo(voteDto.getCandidateInfo())
//      .agreeYn(voteDto.isAgreeYn())
//      .election(voteDto.getElection().getElectionId())
//      .build();
//}

//    public Vote toEntity() {
//        return Vote.builder()
//                .voteId(voteId)
//                .voteTitle(voteTitle)
//                .voteType(voteType)
//                .candidateName(candidateName)
//                .candidateInfo(candidateInfo)
//                .agreeYn(agreeYn)
//                .build();
//    }
}
