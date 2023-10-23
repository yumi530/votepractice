
package com.project.voting.service.voteBox;

import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.List;

public interface VoteBoxService {

  List<VoteBox> getVoteBoxInfo(Long voteId);
  void saveVote(VoteBoxDto voteBoxDto);
  boolean isValid(VoteBoxDto voteBoxDto);

}
