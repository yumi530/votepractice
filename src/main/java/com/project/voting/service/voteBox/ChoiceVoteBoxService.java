package com.project.voting.service.voteBox;


import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChoiceVoteBoxService extends CommonVoteBoxService {

  @Override
  public boolean isValid(VoteBoxDto voteBoxDto) {
    return super.isValid(voteBoxDto);
  }

  @Override
  public void saveVote(VoteBoxDto voteBoxDto) {
    List<VoteBox> voteBoxes = new ArrayList<>();
    List<Long> candidateIds = voteBoxDto.getCandidateIds();

    for (int i = 0; i < candidateIds.size(); i++) {
      VoteBox voteBox = createVoteBox(voteBoxDto);
      voteBox.setCandidateId(candidateIds.get(i));
      voteBox.setChoices((candidateIds.get(i).equals(voteBoxDto.getChosenCandidateId())) ? 1 : 0);
      voteBox.setScores(0);
      voteBox.setRanks(0);
      voteBoxes.add(voteBox);
    }
    voteBoxRepository.saveAll(voteBoxes);
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.CHOICE;
  }
}
