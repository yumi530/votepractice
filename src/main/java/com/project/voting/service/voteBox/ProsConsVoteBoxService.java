package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import org.springframework.stereotype.Component;

@Component
public class ProsConsVoteBoxService extends CommonVoteBoxService implements VoteBoxService {

  @Override
  public boolean isValid(VoteBoxDto voteBoxDto, String usersPhone) {
    return super.isValid(voteBoxDto, usersPhone);
  }

  @Override
  public void saveVote(VoteBoxDto voteBoxDto) {
    VoteBox voteBox = createVoteBox(voteBoxDto);
    voteBox.setHadChosen(voteBoxDto.isHadChosen());
    voteBox.setCandidateId(voteBoxDto.getCandidateIds().get(0));
    voteBox.setRanks(0);
    voteBox.setScores(0);
    voteBox.setChoices(0);

    voteBoxRepository.save(voteBox);
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.PROS_CONS;
  }

}
