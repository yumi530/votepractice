package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ScoreVoteBoxService extends CreateVoteBoxService {

  @Override
  public void saveVote(VoteBoxDto voteBoxDto) {
    List<VoteBox> voteBoxes = new ArrayList<>();
    List<Long> candidateIds = voteBoxDto.getCandidateIds();
    List<Integer> scores = voteBoxDto.getScores();

    for (int i = 0; i < candidateIds.size(); i++) {
      VoteBox voteBox = createVoteBox(voteBoxDto);
      voteBox.setCandidateId(candidateIds.get(i));
      voteBox.setScores(scores != null && !scores.isEmpty() ? scores.get(i) : 0);
      voteBox.setChoices(0);
      voteBox.setRanks(0);
      voteBoxes.add(voteBox);
    }
    voteBoxRepository.saveAll(voteBoxes);
  }

  @Override
  public VoteType getVoteType() {
    return VoteType.SCORE;
  }
}
