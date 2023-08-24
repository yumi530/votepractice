package com.project.voting.dto.election;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.vote.VoteDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectionDto {
    private Long electionId;
    @NotBlank(message = "선거명은 필수 입력 값입니다.")
    private String electionTitle;
    @NotBlank(message = "기관명은 필수 입력 값입니다.")
    private String groupName;
//    @NotBlank(message = "선거 시작일은 필수 입력 값입니다.")
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private String electionStartDt;
//    @NotBlank(message = "선거 종료일은 필수 입력 값입니다.")
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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
