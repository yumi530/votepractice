package com.project.voting.service.candidate;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService{

  private final CandidateRepository candidateRepository;

  @Override
  public Candidate detail(Long voteId) {
    Candidate candidate = candidateRepository.findByVoteId(voteId);
    return candidate;
  }

}
