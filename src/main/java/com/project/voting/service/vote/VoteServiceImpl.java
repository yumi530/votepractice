package com.project.voting.service.vote;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.dto.count.CountDto;
import com.project.voting.dto.vote.VoteDto;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

  private final VoteRepository voteRepository;

  @Override
  public void deleteVote(Long voteId) {
    voteRepository.deleteById(voteId);
  }

  @Override
  public Vote save(VoteDto voteDto) {
    Vote vote = Vote.toEntity(voteDto);
    return voteRepository.save(vote);
  }

  @Override
  public List<Vote> detail(List<Long> voteIds) {
    List<Vote> voteList = voteRepository.findAllById(voteIds);
    return voteList;
  }

  @Override
  public Vote detail(Long voteId) {
    Vote vote = voteRepository.findByVoteId(voteId);
    return vote;
  }
}

