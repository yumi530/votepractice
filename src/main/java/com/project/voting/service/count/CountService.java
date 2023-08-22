package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.count.CountDto;

public interface CountService {

  Count save(boolean isAgreed, Long voteId);

}
