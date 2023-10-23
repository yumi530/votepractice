package com.project.voting.service.new_votebox;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.exception.vote_box.VoteBoxCustomException;
import com.project.voting.exception.vote_box.VoteBoxErrorCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("commonVoteBoxService")
@RequiredArgsConstructor
public abstract class CommonVoteBoxService {
  private final VoteBoxRepository voteBoxRepository;
  private final CandidateRepository candidateRepository;

  public abstract void saveVote(VoteBoxDto voteBoxDto);

  public List<VoteBox> getVoteBoxInfo(Long voteId) {
    return voteBoxRepository.findAllByVoteId(voteId);
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
