package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandCountServiceImpl implements CandCountService {

  private final ElectionRepository electionRepository;
  private final VoteBoxRepository voteBoxRepository;
  private final CandCountRepository candCountRepository;
  private final CountRepository countRepository;

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
      List<CandCount> candCounts = new ArrayList<>();

      for (VoteBox candId : candidateIds) {
        Integer sumChoices = voteBoxRepository.sumChoicesByCandidateId(candId.getCandidateId());
        double choicesAvg = sumChoices / (double) candidateIds.size();

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(candId.getCandidateId());
        candCount.setChoicesAvg(choicesAvg);

        candCounts.add(candCount);
      }

      Collections.sort(candCounts, Comparator.comparingDouble(CandCount::getChoicesAvg).reversed());

      for (int i = 0; i < candCounts.size(); i++) {
        CandCount candCount = candCounts.get(i);
        candCount.setTotalRank(i + 1);
        candCountRepository.save(candCount);
      }



//      Map<Double, Integer> rankMap = new HashMap<>();
//      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);
//
//// 각 candidateId에 대한 평균을 계산하고 rankMap에 저장
//      for (VoteBox candId : candidateIds) {
//        Integer sumRanks = voteBoxRepository.sumRanksByCandidateId(candId.getCandidateId());
//        double ranksAvg = sumRanks / (double) candidateIds.size();
//        rankMap.put(candId.getCandidateId(), ranksAvg);
//      }
//
//// rankMap을 평균값을 기준으로 정렬
//      List<Map.Entry<Integer, Double>> sortedRankList = new ArrayList<>(rankMap.entrySet());
//      sortedRankList.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));
//
//// 각 candidateId에 대한 순위를 계산하고 저장
//      int rank = 1;
//      for (Map.Entry<Integer, Double> entry : sortedRankList) {
//        Integer candidateId = entry.getKey();
//        Double ranksAvg = entry.getValue();
//
//        CandCount candCount = new CandCount();
//        candCount.setElectionId(electionId);
//        candCount.setVoteId(voteId);
//        candCount.setCandidateId(candidateId);
//        candCount.setRanksAvg(ranksAvg);
//        candCount.setTotalRank(rank);
//
//        candCountRepository.save(candCount);
//        rank++;



    } else if (voteType == VoteType.SCORE) {

      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);

      List<Double> avgList = new ArrayList<>();

      for (VoteBox candId : candidateIds) {
        Integer sumScores = voteBoxRepository.sumScoresByCandidateId(candId.getCandidateId());
        double scoresAvg = sumScores / (double) candidateIds.size();
        avgList.add(scoresAvg);
      }

      Collections.sort(avgList, Collections.reverseOrder());

      Map<Double, Integer> scoreMap = new HashMap<>();
      for (int i = 0; i < avgList.size(); i++) {
        double scoresAvg = avgList.get(i);
        scoreMap.put(scoresAvg, i + 1);
      }
      for (VoteBox candId : candidateIds) {
        double scoresAvg = avgList.get(candidateIds.indexOf(candId));
        int rank = scoreMap.get(scoresAvg);

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(candId.getCandidateId());
        candCount.setScoresAvg(scoresAvg);
        candCount.setTotalRank(rank / scoreMap.size());

        candCountRepository.save(candCount);
      }
    } else if (voteType == VoteType.PREFERENCE) {

      List<VoteBox> candidateIds = voteBoxRepository.findAllCandidateIdsByVoteId(voteId);

      List<Double> avgList = new ArrayList<>();

      for (VoteBox candId : candidateIds) {
        Integer sumRanks = voteBoxRepository.sumRanksByCandidateId(candId.getCandidateId());
        double ranksAvg = sumRanks / (double) candidateIds.size();
        avgList.add(ranksAvg);
      }

      Collections.sort(avgList, Collections.reverseOrder());

      Map<Double, Integer> rankMap = new HashMap<>();
      for (int i = 0; i < avgList.size(); i++) {
        double ranksAvg = avgList.get(i);
        rankMap.put(ranksAvg, i + 1);
      }
      for (VoteBox candId : candidateIds) {
        double ranksAvg = avgList.get(candidateIds.indexOf(candId));
        int rank = rankMap.get(ranksAvg);

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(candId.getCandidateId());
        candCount.setRanksAvg(ranksAvg);
        candCount.setTotalRank(rank / rankMap.size());

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
