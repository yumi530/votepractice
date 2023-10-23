package com.project.voting.service.voteBox;

import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;

import java.util.List;

public interface VoteBoxService {

  void saveVote(VoteBoxDto voteBoxDto);

//    VoteBox saveProsCons(VoteBoxDto voteBoxDto);

//    List<VoteBox> save(VoteBoxDto voteBoxDto);

  List<VoteBox> detailVoteBox(Long voteId);


}
