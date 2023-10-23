package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.exception.vote_box.VoteBoxCustomException;
import com.project.voting.exception.vote_box.VoteBoxErrorCode;

import java.util.ArrayList;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteBoxServiceImpl implements VoteBoxService {

  private final VoteBoxRepository voteBoxRepository;

  public void saveVote(VoteBoxDto voteBoxDto) {
    if (voteBoxDto.getVoteType() == VoteType.PROS_CONS) {
      saveProsCons(voteBoxDto);
    } else {
      save(voteBoxDto);
    }
  }

  private VoteBox saveProsCons(VoteBoxDto voteBoxDto) {

    checkDoubleVote(voteBoxDto);

    VoteBox voteBox = new VoteBox();
    voteBox.setElectionId(voteBoxDto.getElectionId());
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    voteBox.setHadChosen(voteBoxDto.isHadChosen());
    voteBox.setCandidateId(voteBoxDto.getCandidateIds().get(0));
    voteBox.setRanks(0);
    voteBox.setScores(0);
    voteBox.setChoices(0);

    return voteBoxRepository.save(voteBox);
  }


  private List<VoteBox> save(VoteBoxDto voteBoxDto) {

    checkDoubleVote(voteBoxDto);

    List<VoteBox> voteBoxes = new ArrayList<>();
    List<Long> candidateIds = voteBoxDto.getCandidateIds();
    List<Integer> scores = voteBoxDto.getScores();
    List<Integer> ranks = voteBoxDto.getRanks();

    for (int i = 0; i < candidateIds.size(); i++) {
      VoteBox voteBox = new VoteBox();
      voteBox.setVoteId(voteBoxDto.getVoteId());
      voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
      voteBox.setChoices((candidateIds.get(i).equals(voteBoxDto.getChosenCandidateId())) ? 1 : 0);
      voteBox.setElectionId(voteBoxDto.getElectionId());
      voteBox.setCandidateId(candidateIds.get(i));
      voteBox.setScores(scores != null && !scores.isEmpty() ? scores.get(i) : 0);
      voteBox.setRanks(ranks != null && !ranks.isEmpty() ? ranks.get(i) : 0);
      voteBoxes.add(voteBox);
    }

    return voteBoxRepository.saveAll(voteBoxes);
  }

  @Override
  public List<VoteBox> detailVoteBox(Long voteId) {
    return voteBoxRepository.findAllByVoteId(voteId);
  }

  private void checkDoubleVote(VoteBoxDto voteBoxDto) {
    Optional<VoteBox> optionalVoteBox = voteBoxRepository.findByCandidateIdAndUsersPhone(
      voteBoxDto.getCandidateId(), voteBoxDto.getUsersPhone());
    if (optionalVoteBox.isPresent()) {
      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_DUPLICATED);
    }
  }
}
