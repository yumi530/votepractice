package com.project.voting.service.candidate;

import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService{

  private final CandidateRepository candidateRepository;

  @Override
  public List<Candidate> detail(Long voteId) {
    List<Candidate> candidate = candidateRepository.findAllByVoteId(voteId);
    return candidate;
  }

  @Override
  public List<Candidate> details(Long voteId) {
    return candidateRepository.findAllCandidateIdsByVoteId(voteId);
  }

  @Override
  public Candidate candidateLength(Long voteId) {
    return candidateRepository.countCandidateIdByVoteId(voteId);
  }

}
