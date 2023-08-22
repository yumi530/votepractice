package com.project.voting.dto.election;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.vote.VoteDto;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectionDto {
    private Long electionId;
    private String electionTitle;
    private String groupName;
    private String electionStartDt;
    private String electionEndDt;
    private List<VoteDto> votes;
    private String usersPhone;

    public static ElectionDto of(Election election){
        return ElectionDto.builder()
          .electionId(election.getElectionId())
          .electionTitle(election.getElectionTitle())
          .groupName(election.getGroupName())
          .electionStartDt(election.getElectionStartDt())
          .electionEndDt(election.getElectionEndDt())
          .usersPhone(election.getUsersPhone())
//          .votes(election.getVotes())
          .build();
    }
    public static List<ElectionDto> of(List<Election> electionList) {
        if (electionList == null) {
            return null;
        }
        List<ElectionDto> electionDtoList = new ArrayList<>();
        for (Election election : electionList) {
            electionDtoList.add(of(election));
        }
        return electionDtoList;
    }

}
