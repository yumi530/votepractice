package com.project.voting.service.voteBox;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.DefaultVoteBoxDto;
import com.project.voting.dto.voteBox.ProsConsVoteBoxDto;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.exception.vote_box.VoteBoxCustomException;
import com.project.voting.exception.vote_box.VoteBoxErrorCode;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteBoxServiceImpl implements VoteBoxService {
  private final VoteBoxRepository voteBoxRepository;
  private final CandidateRepository candidateRepository;
  private final VoteBoxSaver voteBoxSaver;

  @Override
  public List<VoteBox> getVoteBoxInfo(Long voteId) {
    return voteBoxRepository.findAllByVoteId(voteId);
  }

  @Override
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
  @Override
  public void saveVote(VoteBoxDto voteBoxDto) {
    if (voteBoxDto.getVoteType() == VoteType.PROS_CONS) {
      voteBoxSaver.saveProsCons((ProsConsVoteBoxDto) voteBoxDto);
    } else {
      voteBoxSaver.saveDefault((DefaultVoteBoxDto) voteBoxDto);
    }
  }
}
