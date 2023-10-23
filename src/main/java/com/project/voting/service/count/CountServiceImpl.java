package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.dto.voteBox.VoteBoxDto;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.cand_count.CandCountErrorCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

  private final CountRepository countRepository;
  private final CandCountRepository candCountRepository;
  private final ElectionRepository electionRepository;
  private final CountSorter countSorter;

  @Override
  public Count votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {

    Election election = electionRepository.findById(electionId).get();

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
    }

    List<CandCount> candCounts = candCountRepository.findByResult(true);

    List<CandCount> candidateIds = candCountRepository.findAllCandidateIdsByVoteId(voteId);

    if (voteType == VoteType.PROS_CONS) {
      for (CandCount candidate : candidateIds) {
        Count count = createCount(electionId, voteId, candidate.getCandidateId());

        for (CandCount candCount : candCounts) {
          count.setElectedYn(candCount.isResult());
        }
        countRepository.save(count);
      }
    } else if (voteType == VoteType.CHOICE) {
      List<CandCount> sortedCandidates = countSorter.sortCandidatesByChoiceAvg(candidateIds);

      for (CandCount candidate : sortedCandidates) {
        Count count = createCount(electionId, voteId, candidate.getCandidateId());
        if (sortedCandidates.indexOf(candidate) == 0) {
          count.setElectedYn(true);
        } else {
          count.setElectedYn(false);
        }
        countRepository.save(count);
      }

    } else if (voteType == VoteType.SCORE || voteType == VoteType.PREFERENCE) {
      List<CandCount> sortedCandidates = countSorter.sortCandidatesByAvg(candidateIds, voteType);

      for (CandCount candidate : sortedCandidates) {
        Count count = createCount(electionId, voteId, candidate.getCandidateId());
        count.setTotalRank(sortedCandidates.indexOf(candidate) + 1);
        countRepository.save(count);
      }
    }
      return new Count();
  }

  private Count createCount(Long electionId, Long voteId, Long candidateId) {
    Count count = new Count();
    count.setElectionId(electionId);
    count.setVoteId(voteId);
    count.setCandidateId(candidateId);
    return count;
  }

  @Override
  public List<Count> details(Long voteId) {

    return countRepository.findAllCandidateIdsByVoteId(voteId);
  }


}




