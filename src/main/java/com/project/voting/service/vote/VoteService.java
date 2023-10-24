package com.project.voting.service.vote;


import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.vote.VoteDto;
import java.util.List;
import org.springframework.stereotype.Service;

public interface VoteService {

  Vote save(VoteDto voteDto);

  List<Vote> detail(List<Long> voteIds);

  Vote detail(Long voteId);

}
