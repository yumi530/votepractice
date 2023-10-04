package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
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
  public CandCount countVotesResult(Long voteId, Long electionId,
    VoteType voteType) {
    Election election = electionRepository.findById(electionId).get();

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new RuntimeException("선거 종료일 전에는 개표할 수 없습니다.");
    }
    if (voteType == VoteType.PROS_CONS) {
      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);

      for (VoteBox candId : candidateIds) {
        Long countIds = voteBoxRepository.countAllByVoteId(voteId);
        Long countPros = voteBoxRepository.countByIsAgreedTrueAndCandidateId(
          candId.getCandidateId());
        Long countCons = voteBoxRepository.countByIsAgreedFalseAndCandidateId(
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
    } else if (voteType == VoteType.CHOICE) {

      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);
      TreeSet<Double> avgList = new TreeSet<>(Collections.reverseOrder());

      for (VoteBox candId : candidateIds) {

        Integer sumChoices = voteBoxRepository.sumChoicesByCandidateId(candId.getCandidateId());
        Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
          candId.getCandidateId());

        double avg = (double) sumChoices / (double) usersPhones;
        avgList.add(avg);


        int rank = 1;

        for (double rankAvg : avgList) {

          CandCount candCount = new CandCount();
          candCount.setElectionId(electionId);
          candCount.setVoteId(voteId);
          candCount.setCandidateId(candId.getCandidateId());
          candCount.setChoicesAvg(rankAvg);
          candCount.setTotalRank(rank++);

          candCountRepository.save(candCount);

        }
      }
    } else if (voteType == VoteType.SCORE) {

      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);
      TreeSet<Double> avgList = new TreeSet<>(Collections.reverseOrder());

      for (VoteBox candId : candidateIds) {
        Integer sumScores = voteBoxRepository.sumScoresByCandidateId(candId.getCandidateId());
        Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
          candId.getCandidateId());

        double avg = (double) sumScores / (double) usersPhones;
        avgList.add(avg);

        int rank = 1;

        for (double rankAvg : avgList) {

          CandCount candCount = new CandCount();
          candCount.setElectionId(electionId);
          candCount.setVoteId(voteId);
          candCount.setCandidateId(candId.getCandidateId());
          candCount.setScoresAvg(rankAvg);
          candCount.setTotalRank(rank++);

          candCountRepository.save(candCount);

        }
      }
    } else if (voteType == VoteType.PREFERENCE) {

      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);
      TreeSet<Double> avgList = new TreeSet<>(Collections.reverseOrder());

      for (VoteBox candId : candidateIds) {

        Integer sumRanks = voteBoxRepository.sumRanksByCandidateId(candId.getCandidateId());
        Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(
          candId.getCandidateId());

        double avg = (double) sumRanks / (double) usersPhones;
        avgList.add(avg);

        int rank = 1;

        for (double rankAvg : avgList) {

          CandCount candCount = new CandCount();
          candCount.setElectionId(electionId);
          candCount.setVoteId(voteId);
          candCount.setCandidateId(candId.getCandidateId());
          candCount.setRanksAvg(rankAvg);
          candCount.setTotalRank(rank++);

          candCountRepository.save(candCount);
        }
      }
    }

    return new CandCount();
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
