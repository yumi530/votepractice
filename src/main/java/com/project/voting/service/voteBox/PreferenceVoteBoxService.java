package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PreferenceVoteBoxService extends CommonVoteBoxService implements VoteBoxService {

  @Override
  public boolean isValid(VoteBoxDto voteBoxDto, String usersPhone) {
    return super.isValid(voteBoxDto, usersPhone);
  }

  @Override
  public void saveVote(VoteBoxDto voteBoxDto) {
    List<VoteBox> voteBoxes = new ArrayList<>();
    List<Long> candidateIds = voteBoxDto.getCandidateIds();
    List<Integer> ranks = voteBoxDto.getRanks();

    for (int i = 0; i < candidateIds.size(); i++) {
      VoteBox voteBox = createVoteBox(voteBoxDto);
      voteBox.setCandidateId(candidateIds.get(i));
      voteBox.setChoices(0);
      voteBox.setScores(0);
      voteBox.setRanks(ranks != null && !ranks.isEmpty() ? ranks.get(i) : 0);
      voteBoxes.add(voteBox);
    }
     voteBoxRepository.saveAll(voteBoxes);
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.PREFERENCE;
  }
}
