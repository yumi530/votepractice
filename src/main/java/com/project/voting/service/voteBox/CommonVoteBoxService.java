package com.project.voting.service.voteBox;

import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.List;

public interface CommonVoteBoxService {

  List<VoteBox> getVoteBoxInfo(Long voteId);

  boolean isValid(VoteBoxDto voteBoxDto);

}
