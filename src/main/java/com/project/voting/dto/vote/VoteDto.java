package com.project.voting.dto.vote;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.vote.VoteType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {

    private Long voteId;
    @NotNull(message = "투표명은 필수 입력 값입니다.")
    private String voteTitle;
    private String candidateName;
    private String candidateInfo;
    private Long electionId;
    private String voteType;
    private List<String> voteTypes;
    private List<String> candidateNames;
    private List<String> candidateInfos;



}
