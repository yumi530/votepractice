package com.project.voting.service.new_votebox;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.exception.vote_box.VoteBoxCustomException;
import com.project.voting.exception.vote_box.VoteBoxErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VoteBoxSaver {

  private final VoteBoxRepository voteBoxRepository;
  private final CandidateRepository candidateRepository;

  public VoteBox saveProsCons(VoteBoxDto voteBoxDto) {
    VoteBox voteBox = createVoteBox(voteBoxDto);
    voteBox.setHadChosen(voteBoxDto.isHadChosen());
    voteBox.setCandidateId(voteBoxDto.getCandidateIds().get(0));
    voteBox.setRanks(0);
    voteBox.setScores(0);
    voteBox.setChoices(0);

    return voteBoxRepository.save(voteBox);
  }

  public List<VoteBox> saveDefault(VoteBoxDto voteBoxDto) {
    List<VoteBox> voteBoxes = new ArrayList<>();
    List<Long> candidateIds = voteBoxDto.getCandidateIds();
    List<Integer> scores = voteBoxDto.getScores();
    List<Integer> ranks = voteBoxDto.getRanks();

    for (int i = 0; i < candidateIds.size(); i++) {
      VoteBox voteBox = createVoteBox(voteBoxDto);
      voteBox.setChoices((candidateIds.get(i).equals(voteBoxDto.getChosenCandidateId())) ? 1 : 0);
      voteBox.setCandidateId(candidateIds.get(i));
      voteBox.setScores(scores != null && !scores.isEmpty() ? scores.get(i) : 0);
      voteBox.setRanks(ranks != null && !ranks.isEmpty() ? ranks.get(i) : 0);
      voteBoxes.add(voteBox);
    }

    return voteBoxRepository.saveAll(voteBoxes);
  }

  private VoteBox createVoteBox(VoteBoxDto voteBoxDto) {
    VoteBox voteBox = new VoteBox();
    voteBox.setElectionId(voteBoxDto.getElectionId());
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    return voteBox;
  }

  public boolean isValid(VoteBoxDto voteBoxDto) {

    Optional<VoteBox> optionalVoteBox = voteBoxRepository.findByCandidateIdAndUsersPhone(
      voteBoxDto.getCandidateId(), voteBoxDto.getUsersPhone());
    if (optionalVoteBox.isPresent()) {
      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_DUPLICATED);
    }
    List<Candidate> candidateList = candidateRepository.findAllCandidateIdByVoteId(
      voteBoxDto.getVoteId());
    if (candidateList == null) {
      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_NOT_FOUND);
    }
    return true;
  }
}
