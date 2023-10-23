package com.project.voting.service.new_votebox;



import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.service.voteBox.VoteBoxValid;
import org.springframework.stereotype.Service;

@Service("defaultVoteService")
public class DefaultVoteService extends CommonVoteBoxService {
  private final VoteBoxSaver voteBoxSaver;
  private final VoteBoxValid voteBoxValid;

  public DefaultVoteService(VoteBoxRepository voteBoxRepository, CandidateRepository candidateRepository, VoteBoxSaver voteBoxSaver,
    VoteBoxValid voteBoxValid) {
    super(voteBoxRepository, candidateRepository);
    this.voteBoxSaver = voteBoxSaver;
    this.voteBoxValid = voteBoxValid;
  }

  @Override
  public void saveVote(VoteBoxDto voteBoxDto) {
    voteBoxValid.isValid(voteBoxDto);
    if (voteBoxDto.getVoteType() != VoteType.PROS_CONS) {
      voteBoxSaver.saveDefault(voteBoxDto);
    }
  }
}


