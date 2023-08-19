package com.project.voting.dto.vote;

import lombok.Data;

@Data
public class VoteDto {

    private Long voteId;
    private String voteTitle;
    private String candidateName;
    private String candidateInfo;
    private Long electionId;

}
