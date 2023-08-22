package com.project.voting.service.count;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.dto.count.CountDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

  private final CountRepository countRepository;
  private final VoteRepository voteRepository;

  @Override
  public Count save(boolean isAgreed, Long voteId) {
    Vote vote = voteRepository.findById(voteId).orElse(null);
    if (vote == null) {
      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
    }
    Count count = toCount(isAgreed, vote);
    return countRepository.save(count);

  }

  private Count toCount(boolean isAgreed, Vote vote){
    return Count.builder()
      .isAgreed(isAgreed)
      .vote(vote)
      .build();
  }
}
