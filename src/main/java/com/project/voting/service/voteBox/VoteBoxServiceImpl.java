package com.project.voting.service.voteBox;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
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
    Candidate candidate = candidateRepository.findByVoteId(voteBoxDto.getVoteId());
    if (candidate == null) {
      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
    }
    List<Candidate> candidateList = candidateRepository.findAllByVoteId(voteBoxDto.getVoteId());

    List<VoteBox> voteBoxes = new ArrayList<>();

    if (candidateList != null && !candidateList.isEmpty()) {
      for (Candidate c : candidateList) {
        VoteBox voteBox = toVoteBox(voteBoxDto, c);
        voteBoxes.add(voteBox);
      }
    } else {
      VoteBox voteBox = toVoteBox(voteBoxDto, candidate);
      voteBoxes.add(voteBox);
    }

    return voteBoxRepository.saveAll(voteBoxes);
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
}
