package com.project.voting.dto.vote;

import com.project.voting.domain.count.Count;
import com.project.voting.dto.count.CountDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {

    private Long voteId;
    @NotBlank(message = "투표명은 필수 입력 값입니다.")
    private String voteTitle;
    @NotBlank(message = "후보자 이름은 필수 입력 값입니다.")
    private String candidateName;
    @NotBlank(message = "후보자 정보는 필수 입력 값입니다.")
    private String candidateInfo;
    private Long electionId;
    private List<Count> counts;
    private String filename;
    private String filepath;


}
