package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    VoteType voteType, Long candidateId) {
    Election election = electionRepository.findById(electionId).get();

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new RuntimeException("선거 종료일 전에는 개표할 수 없습니다.");
    }
    if (voteType == VoteType.PROS_CONS) {
      VoteBox voteBox = voteBoxRepository.findCandidateIdByVoteId(voteId);
      Long countIds = voteBoxRepository.countAllByVoteId(voteId);
      Long countPros = voteBoxRepository.countByIsAgreedTrueAndCandidateId(
        voteBox.getCandidateId());
      Long countCons = voteBoxRepository.countByIsAgreedFalseAndCandidateId(
        voteBox.getCandidateId());

      double prosRatio = pros(countIds, countPros);
      double consRatio = cons(countIds, countCons);

      CandCount candCount = new CandCount();
      candCount.setElectionId(electionId);
      candCount.setVoteId(voteId);
      candCount.setCandidateId(voteBox.getCandidateId());

      candCount.setResult(prosCons(countIds, countPros));
      candCount.setProsRatio(prosRatio);
      candCount.setConsRatio(consRatio);

      return candCountRepository.save(candCount);

    } else if (voteType == VoteType.CHOICE) {

      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);

      for (VoteBox candId : candidateIds) {
        Integer sumChoices = voteBoxRepository.sumChoicesByCandidateId(candId.getCandidateId());

        double choicesAvg = sumChoices / (double) candidateIds.size();

        List<Double> avgList = new ArrayList<>();
        for (VoteBox id : candidateIds) {
          Integer sum = voteBoxRepository.sumChoicesByCandidateId(id.getCandidateId());
          avgList.add(sum / (double) candidateIds.size());
        }
        Collections.sort(avgList, Collections.reverseOrder());

        int rank = avgList.indexOf(choicesAvg) + 1;

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(candId.getCandidateId());
        candCount.setChoicesAvg(choicesAvg);
        candCount.setTotalRank(rank);

        candCountRepository.save(candCount);
      }

    } else if (voteType == VoteType.SCORE) {

      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);

      for (VoteBox candId : candidateIds) {
        Integer sumScores = voteBoxRepository.sumScoresByCandidateId(candId.getCandidateId());

        double scoresAvg = sumScores / (double) candidateIds.size();

        List<Double> avgList = new ArrayList<>();
        for (VoteBox id : candidateIds) {
          Integer sum = voteBoxRepository.sumScoresByCandidateId(id.getCandidateId());
          avgList.add(sum / (double) candidateIds.size());
        }
        Collections.sort(avgList, Collections.reverseOrder());

        int rank = avgList.indexOf(scoresAvg) + 1;

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(candId.getCandidateId());
        candCount.setScoresAvg(scoresAvg);
        candCount.setTotalRank(rank);

        candCountRepository.save(candCount);
      }
    } else if (voteType == VoteType.PREFERENCE) {

      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);

      for (VoteBox candId : candidateIds) {
        Integer sumRanks = voteBoxRepository.sumRanksByCandidateId(candId.getCandidateId());

        double ranksAvg = sumRanks / (double) candidateIds.size();

        List<Double> avgList = new ArrayList<>();
        for (VoteBox id : candidateIds) {
          Integer sum = voteBoxRepository.sumRanksByCandidateId(id.getCandidateId());
          avgList.add(sum / (double) candidateIds.size());
        }
        Collections.sort(avgList, Collections.reverseOrder());

        int rank = avgList.indexOf(ranksAvg) + 1;

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(candId.getCandidateId());
        candCount.setRanksAvg(ranksAvg);
        candCount.setTotalRank(rank);

        candCountRepository.save(candCount);
      }
    }
    return new CandCount();
  }

//  @Override
//  public Vote countVotesResultConfirm(Long voteId) {
//    Vote votes = voteRepository.findById(voteId).get();
//    return voteRepository.save(votes);
//  }

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
