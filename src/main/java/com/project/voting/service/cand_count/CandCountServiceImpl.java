package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import com.project.voting.exception.cand_count.CandCountCustomException;
import com.project.voting.exception.cand_count.CandCountErrorCode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandCountServiceImpl implements CandCountService {

  private final ElectionRepository electionRepository;
  private final VoteBoxRepository voteBoxRepository;
  private final CandCountRepository candCountRepository;

  @Override
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  public void countVotesResult(Long voteId, Long electionId,
    VoteType voteType) {

    Election election = electionRepository.findById(electionId).get();

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
    }

    List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);
    TreeSet<Double> avgList = new TreeSet<>(Collections.reverseOrder());
    int rank = 1;

    switch (voteType) {
      case PROS_CONS:

        for (VoteBox candId : candidateIds) {
          Long countIds = voteBoxRepository.countAllByVoteId(voteId);
          Long countPros = voteBoxRepository.countByHadChosenTrueAndCandidateId(
            candId.getCandidateId());
          Long countCons = voteBoxRepository.countByHadChosenFalseAndCandidateId(
            candId.getCandidateId());

          double prosRatio = pros(countIds, countPros);
          double consRatio = cons(countIds, countCons);

          CandCount candCount = new CandCount();
          candCount.setElectionId(electionId);
          candCount.setVoteId(voteId);
          candCount.setCandidateId(candId.getCandidateId());

          candCount.setResult(prosCons(countIds, countPros));
          candCount.setProsRatio(prosRatio);
          candCount.setConsRatio(consRatio);

          candCountRepository.save(candCount);
        }
        break;

      case CHOICE:

        for (VoteBox candId : candidateIds) {

          Integer sumChoices = voteBoxRepository.sumChoicesByCandidateId(candId.getCandidateId());
          Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
            candId.getCandidateId());

          double avg = (double) sumChoices / (double) usersPhones;
          avgList.add(avg);
        }

        for (Double avg : avgList) {
          for (VoteBox candId : candidateIds) {
            Integer sumChoices = voteBoxRepository.sumChoicesByCandidateId(candId.getCandidateId());
            Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
              candId.getCandidateId());

            double candidateAvg = (double) sumChoices / (double) usersPhones;

            if (avg == candidateAvg) {
              CandCount candCount = new CandCount();
              candCount.setElectionId(electionId);
              candCount.setVoteId(voteId);
              candCount.setCandidateId(candId.getCandidateId());
              candCount.setChoicesAvg(avg);
              candCount.setTotalRank(rank);

              candCountRepository.save(candCount);
            }
          }
          rank++;
        }
        break;

      case SCORE:

        for (VoteBox candId : candidateIds) {
          Integer sumScores = voteBoxRepository.sumScoresByCandidateId(candId.getCandidateId());
          Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
            candId.getCandidateId());

          double avg = (double) sumScores / (double) usersPhones;
          avgList.add(avg);
        }

        for (Double avg : avgList) {
          for (VoteBox candId : candidateIds) {
            Integer sumSCores = voteBoxRepository.sumScoresByCandidateId(candId.getCandidateId());
            Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
              candId.getCandidateId());

            double candidateAvg = (double) sumSCores / (double) usersPhones;

            if (avg == candidateAvg) {
              CandCount candCount = new CandCount();
              candCount.setElectionId(electionId);
              candCount.setVoteId(voteId);
              candCount.setCandidateId(candId.getCandidateId());
              candCount.setScoresAvg(avg);
              candCount.setTotalRank(rank);

              candCountRepository.save(candCount);
            }
          }
          rank++;
        }
        break;

      case PREFERENCE:

        for (VoteBox candId : candidateIds) {

          Integer sumRanks = voteBoxRepository.sumRanksByCandidateId(candId.getCandidateId());
          Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
            candId.getCandidateId());

          double avg = (double) sumRanks / (double) usersPhones;
          avgList.add(avg);
        }

        for (Double avg : avgList) {
          for (VoteBox candId : candidateIds) {
            Integer sumRanks = voteBoxRepository.sumRanksByCandidateId(candId.getCandidateId());
            Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
              candId.getCandidateId());

            double candidateAvg = (double) sumRanks / (double) usersPhones;

            if (avg == candidateAvg) {
              CandCount candCount = new CandCount();
              candCount.setElectionId(electionId);
              candCount.setVoteId(voteId);
              candCount.setCandidateId(candId.getCandidateId());
              candCount.setRanksAvg(avg);
              candCount.setTotalRank(rank);

              candCountRepository.save(candCount);
            }
          }
          rank++;
        }
        break;
      default: throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_NOT_VALID);
    }
  }


  @Override
  public List<CandCount> details(Long voteId) {
    return candCountRepository.findAllCandidateIdsByVoteId(voteId);
  }

  @Override
  public CandCount detail(Long voteId) {
    return candCountRepository.findCandidateIdByVoteId(voteId);
  }

  private boolean prosCons(Long countIds, Long countPros) {
    if ((countIds / 2) < countPros) {
      return true;
    }
    return false;
  }

  private double pros(Long countIds, Long countPros) {
    return ((double) countPros / countIds) * 100.0;
  }

  private double cons(Long countIds, Long countCons) {
    return ((double) countCons / countIds) * 100.0;
  }

}
