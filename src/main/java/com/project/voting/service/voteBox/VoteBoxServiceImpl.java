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
  public List<VoteBox> save(VoteBoxDto voteBoxDto) {

    List<Candidate> candidateList = candidateRepository.findAllCandidateIdByVoteId(voteBoxDto.getVoteId());

        if (candidateList == null) {
      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
    }

    List<VoteBox> voteBoxes = new ArrayList<>();

    if (voteBoxDto.getVoteType() == VoteType.PROS_CONS || voteBoxDto.getVoteType() == VoteType.CHOICE) {

      if (candidateList != null && !candidateList.isEmpty()) {
        for (Candidate c : candidateList) {
          VoteBox voteBox = toVoteBox(voteBoxDto, c);
          voteBoxes.add(voteBox);
        }
      }
    }
    else if (voteBoxDto.getVoteType() == VoteType.SCORE) {
      if (candidateList != null && !candidateList.isEmpty()) {
        List<Integer> scoresList = voteBoxDto.getScores();
        for (int i = 0; i < candidateList.size(); i++) {
          Integer score = scoresList.get(i);
          Candidate c = candidateList.get(i);
          VoteBox voteBox = toVoteBoxScores(voteBoxDto, c, score);
          voteBoxes.add(voteBox);
        }
      }
    }

    else if (voteBoxDto.getVoteType() == VoteType.PREFERENCE){
      if (candidateList != null && !candidateList.isEmpty()) {
        for (Candidate c : candidateList) {
          VoteBox voteBox = toVoteBoxRank(voteBoxDto, c);
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

  private VoteBox toVoteBox(VoteBoxDto voteBoxDto, Candidate candidate) {

    VoteBox voteBox = new VoteBox();
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setHadChosen(voteBoxDto.isHadChosen());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    voteBox.setHadVoted(voteBox.isHadVoted());
    voteBox.setElectionId(candidate.getElectionId());
    voteBox.setCandidateId(candidate.getCandidateId());

    return voteBox;
  }

  private VoteBox toVoteBoxRank(VoteBoxDto voteBoxDto, Candidate candidate) {

    VoteBox voteBox = new VoteBox();
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    voteBox.setHadVoted(voteBoxDto.isHadVoted());
    voteBox.setElectionId(candidate.getElectionId());
    voteBox.setCandidateId(candidate.getCandidateId());
    voteBox.setRanks(voteBoxDto.getRanks());


      return voteBox;
  }

  private VoteBox toVoteBoxScores(VoteBoxDto voteBoxDto, Candidate candidate, VoteBox score) {

    VoteBox voteBox = new VoteBox();
    voteBox.setVoteId(voteBoxDto.getVoteId());
    voteBox.setScores(voteBoxDto.getScores());
    voteBox.setUsersPhone(voteBoxDto.getUsersPhone());
    voteBox.setHadVoted(voteBoxDto.isHadVoted());
    voteBox.setElectionId(candidate.getElectionId());
    voteBox.setCandidateId(candidate.getCandidateId());

    return voteBox;
  }
}
