package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.exception.vote_box.VoteBoxCustomException;
import com.project.voting.exception.vote_box.VoteBoxErrorCode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class PreferenceVoteBoxService extends VoteBoxService {

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
        voteBox.setRanks(isRankListValid(ranks,candidateIds.size()) ? ranks.get(i) : 0);
        voteBoxes.add(voteBox);
      }

    voteBoxRepository.saveAll(voteBoxes);
  }


  @Override
  public VoteType getVoteType() {
    return VoteType.PREFERENCE;
  }

  private boolean isRankListValid(List<Integer> ranks, int candidateCount) {
    Set<Integer> usedRanks = new HashSet<>();
    for (int rank : ranks) {
      if (rank >= 1 && rank <= candidateCount && !usedRanks.contains(rank)) {
        usedRanks.add(rank);
      } else {
        throw new VoteBoxCustomException(VoteBoxErrorCode.PREFER_NOT_VALID);
      }
    }
    return usedRanks.size() == candidateCount;
  }


}
