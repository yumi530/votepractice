package com.project.voting.service.voteBox;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.exception.vote_box.VoteBoxCustomException;
import com.project.voting.exception.vote_box.VoteBoxErrorCode;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CommonVoteService {

  final VoteBoxRepository voteBoxRepository;
  private final CandidateRepository candidateRepository;
  private final VoteBoxSaver voteSaver;

  protected CommonVoteService(VoteBoxRepository voteBoxRepository,
    CandidateRepository candidateRepository, VoteBoxSaver voteSaver) {
    this.voteBoxRepository = voteBoxRepository;
    this.candidateRepository = candidateRepository;
    this.voteSaver = voteSaver;
  }

  public boolean isValid(VoteBoxDto voteBoxDto) {

    Optional<VoteBox> optionalVoteBox = voteBoxRepository.findByCandidateIdAndUsersPhone(
      voteBoxDto.getCandidateId(), voteBoxDto.getUsersPhone());
    if (optionalVoteBox.isPresent()) {
      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_DUPLICATED);
    }
    List<Candidate> candidateList = candidateRepository.findAllCandidateIdByVoteId(voteBoxDto.getVoteId());
    if (candidateList == null) {
      throw new VoteBoxCustomException(VoteBoxErrorCode.VOTE_NOT_FOUND);
    }
    return true;
  }

    public void doSave(VoteBoxDto voteBoxDto) {
    if (voteBoxDto.getVoteType() == VoteType.PROS_CONS) {
      voteSaver.saveProsCons(voteBoxDto);
    } else {
      voteSaver.saveDefault(voteBoxDto);
    }
  }

  public List<VoteBox> getVoteBoxInfo(Long voteId) {
    return voteBoxRepository.findAllByVoteId(voteId);
  }

}
