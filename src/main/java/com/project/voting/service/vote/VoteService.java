package com.project.voting.service.vote;


import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.vote.VoteDto;
import java.util.List;
import javax.servlet.http.HttpSession;

public interface VoteService {


    void deleteVote(Long voteId);


  Vote save(VoteDto voteDto);

  List<Vote> detail(List<Long> voteIds);

  Vote detail(Long voteId);
}
