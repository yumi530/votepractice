package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;

import com.project.voting.domain.users.UsersRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

  private final CountRepository countRepository;
  private final CandCountRepository candCountRepository;
  private final UsersRepository usersRepository;
  private final VoteBoxRepository voteBoxRepository;

  @Override
  public Count votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {

    List<CandCount> candCounts = candCountRepository.findByResult(true);

    List<CandCount> candidateIds = candCountRepository.findAllCandidateIdsByVoteId(voteId);

    if (voteType == VoteType.PROS_CONS) {
      for (CandCount candidate : candidateIds) {
        Count count = new Count();
        count.setElectionId(electionId);
        count.setVoteId(voteId);
        count.setCandidateId(candidate.getCandidateId());

        for (CandCount candCount : candCounts) {
          count.setElectedYn(candCount.isResult());
        }
        countRepository.save(count);

      }
    } else if (voteType == VoteType.CHOICE) {

      for (CandCount candidate : candidateIds) {
        Count count = new Count();
        count.setVoteId(voteId);
        count.setCandidateId(candidate.getCandidateId());
        count.setElectionId(electionId);
        count.setTotalRank(candidate.getTotalRank());
        if (candidate.getTotalRank() == 1) {
          count.setElectedYn(true);
        } else {
          count.setElectedYn(false);
        }
        countRepository.save(count);
      }
    } else if (voteType == VoteType.SCORE || voteType == VoteType.PREFERENCE) {

      for (CandCount candidate : candidateIds) {
        Count count = new Count();
        count.setVoteId(voteId);
        count.setCandidateId(candidate.getCandidateId());
        count.setElectionId(electionId);
        count.setTotalRank(candidate.getTotalRank());
        countRepository.save(count);
      }
    }
    return new Count();
  }

  @Override
  public List<Count> details(Long voteId) {

    return countRepository.findAllCandidateIdsByVoteId(voteId);
  }

  @Override
  public Count turnOut(Long electionId, Long voteId) {

    List<CandCount> candidateIds = candCountRepository.findAllCandidateIdsByVoteId(voteId);
    Integer numOfElectors = usersRepository.countUsersPhonesByElectionId(
      electionId);

    Integer numOfParticipation = voteBoxRepository.countUsersPhonesByVoteId(voteId) / candidateIds.size();

    double calTurnOut = ((numOfParticipation * 100) / numOfElectors);

    for (CandCount candidate : candidateIds) {
      Count count = new Count();
      count.setElectionId(electionId);
      count.setVoteId(voteId);
      count.setCandidateId(candidate.getCandidateId());
      count.setTurnOut(calTurnOut);

      countRepository.save(count);
    }
    return new Count();
  }
}

