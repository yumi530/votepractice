package com.project.voting.dto.vote;

import com.project.voting.domain.count.Count;
import com.project.voting.dto.count.CountDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {

    private Long voteId;
    private String voteTitle;
    private String candidateName;
    private String candidateInfo;
    private Long electionId;
    private List<Count> counts;


}
