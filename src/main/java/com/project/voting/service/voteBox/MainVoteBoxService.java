package com.project.voting.service.voteBox;

import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.dto.voteBox.VoteBoxDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainVoteBoxService {

  private final VoteBoxServiceFactory voteBoxServiceFactory;
  private final VoteBoxRepository voteBoxRepository;

  public void saveVote(VoteBoxDto voteBoxDto)
  {
    voteBoxServiceFactory.getService(voteBoxDto.getVoteType()).saveVote(voteBoxDto);
  }

  public List<VoteBox> getVoteBoxInfo(Long voteId) {
    return voteBoxRepository.findAllByVoteId(voteId);
  }

}