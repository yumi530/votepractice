package com.project.voting.service.voteBox;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteBoxServiceImpl implements VoteBoxService {

  private final VoteBoxRepository voteBoxRepository;
  private final CandidateRepository candidateRepository;

  @Override
  public List<VoteBox> save(VoteBoxDto voteBoxDto, String usersPhone) {

    List<Candidate> candidateList = candidateRepository.findAllCandidateIdByVoteId(
      voteBoxDto.getVoteId());

    if (candidateList == null) {
      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
    }

    List<VoteBox> voteBoxes = new ArrayList<>();

    if (voteBoxDto.getVoteType() == VoteType.PROS_CONS) {

      if (candidateList != null && !candidateList.isEmpty()) {
        for (int i = 0; i < candidateList.size(); i++) {
          Candidate c = candidateList.get(i);

          Integer rank = 0;
          Integer score = 0;

          VoteBox voteBox = toVoteBox(voteBoxDto, c, rank, score, usersPhone);
          voteBoxes.add(voteBox);
        }
      }
    } else if (voteBoxDto.getVoteType() == VoteType.CHOICE) {
      List<Boolean> choiceList = voteBoxDto.getChoiceList();
      if (choiceList != null && !choiceList.isEmpty()) {
        for (int i = 0; i < candidateList.size(); i++) {
          Candidate c = candidateList.get(i);
          Boolean hadChosen = choiceList.get(i);
          Integer rank = 0;
          Integer score = 0;

          VoteBox voteBox = toVoteBoxChoice(voteBoxDto, c, rank, score, hadChosen);
          voteBoxes.add(voteBox);
        }
      }
    } else if (voteBoxDto.getVoteType() == VoteType.SCORE) {
      List<Integer> scoresList = voteBoxDto.getScoreList();

      if (candidateList != null && !candidateList.isEmpty()) {

        for (int i = 0; i < candidateList.size(); i++) {
          Integer score = scoresList.get(i);
          Candidate c = candidateList.get(i);

          Integer rank = 0;

          VoteBox voteBox = toVoteBoxScores(voteBoxDto, c, score, rank);
          voteBoxes.add(voteBox);
        }
      }
    } else if (voteBoxDto.getVoteType() == VoteType.PREFERENCE) {
      List<Integer> ranksList = voteBoxDto.getRankList();

      if (candidateList != null && !candidateList.isEmpty()) {

        for (int i = 0; i < candidateList.size(); i++) {
          Integer rank = ranksList.get(i);
          Candidate c = candidateList.get(i);

          Integer score = 0;

          VoteBox voteBox = toVoteBoxRank(voteBoxDto, c, score, rank);
          voteBoxes.add(voteBox);
        }
      }
    }
    return voteBoxRepository.saveAll(voteBoxes);
  }


  @Override
  public List<VoteBox> detailVoteBox(Long voteId) {
    return voteBoxRepository.findAllByVoteId(voteId);
  }

  private VoteBox toVoteBox(VoteBoxDto voteBoxDto, Candidate candidate, Integer rank, Integer score, String usersPhone) {

    VoteBox voteBox = new VoteBox();
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setHadChosen(voteBox.isHadChosen());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    voteBox.setHadVoted(true);
    voteBox.setElectionId(candidate.getElectionId());
    voteBox.setCandidateId(candidate.getCandidateId());
    voteBox.setRanks(rank);
    voteBox.setScores(score);
    voteBox.setUsersPhone(usersPhone);

    return voteBox;
  }

  private VoteBox toVoteBoxChoice(VoteBoxDto voteBoxDto, Candidate candidate, Integer rank, Integer score, Boolean hadChosen) {

    VoteBox voteBox = new VoteBox();
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    voteBox.setHadVoted(true);
    voteBox.setHadChosen(hadChosen);
    voteBox.setElectionId(candidate.getElectionId());
    voteBox.setCandidateId(candidate.getCandidateId());
    voteBox.setRanks(rank);
    voteBox.setScores(score);

    return voteBox;
  }

  private VoteBox toVoteBoxRank(VoteBoxDto voteBoxDto, Candidate candidate, Integer score,
    Integer rank) {

    VoteBox voteBox = new VoteBox();
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    voteBox.setHadVoted(true);
    voteBox.setElectionId(candidate.getElectionId());
    voteBox.setCandidateId(candidate.getCandidateId());
    voteBox.setRanks(rank);
    voteBox.setScores(score);

    return voteBox;
  }

  private VoteBox toVoteBoxScores(VoteBoxDto voteBoxDto, Candidate candidate, Integer score,
    Integer rank) {

    VoteBox voteBox = new VoteBox();
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setScores(score);
    voteBox.setRanks(rank);
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    voteBox.setHadVoted(voteBoxDto.isHadVoted());
    voteBox.setElectionId(candidate.getElectionId());
    voteBox.setCandidateId(candidate.getCandidateId());

    return voteBox;
  }

}
