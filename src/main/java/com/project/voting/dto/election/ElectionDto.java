package com.project.voting.dto.election;

import com.project.voting.dto.vote.VoteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectionDto {
    private Long electionId;
    private String electionTitle;
    private String groupName;
    private String electionStartDt;
    private String electionEndDt;
    private List<VoteDto> votes;
    private String filename;
    private String filepath;

}
