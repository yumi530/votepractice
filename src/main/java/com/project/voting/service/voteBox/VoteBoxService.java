package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.List;
import org.springframework.stereotype.Service;

public interface VoteBoxService {

//  List<VoteBox> getVoteBoxInfo(Long voteId);
//
//  boolean isValid(VoteBoxDto voteBoxDto);

  void saveVote(VoteBoxDto voteBoxDto);
  VoteType getVoteType();

}