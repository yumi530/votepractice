package com.project.voting.service.voteBox;

import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.List;

public interface VoteBoxService {

  List<VoteBox> saveProsCons(VoteBoxDto voteBoxDto, String usersPhone);

  VoteBox saveChoice(VoteBoxDto voteBoxDto, String usersPhone, String candidateId);

  List<VoteBox> saveScore(VoteBoxDto voteBoxDto, String usersPhone);

  List<VoteBox> savePrefer(VoteBoxDto voteBoxDto, String usersPhone);

  List<VoteBox> detailVoteBox(Long voteId);


}
