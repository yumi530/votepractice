package com.project.voting.service.candidate;

import com.project.voting.domain.candidate.Candidate;
import java.util.List;

public interface CandidateService {

  List<Candidate> detail(Long candidateId);
}
