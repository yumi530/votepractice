//package com.project.voting.service.cand_count;
//
//import com.project.voting.domain.cand_count.CandCount;
//import com.project.voting.domain.cand_count.CandCountRepository;
//import com.project.voting.domain.vote.VoteType;
//import com.project.voting.domain.voteBox.VoteBox;
//import com.project.voting.domain.voteBox.VoteBoxRepository;
//import com.project.voting.exception.cand_count.CandCountCustomException;
//import com.project.voting.exception.cand_count.CandCountErrorCode;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class CandCountProcess {
//
//  private final VoteBoxRepository voteBoxRepository;
//  private final CandCountRepository candCountRepository;
//
//  public void processProsConsCandCount(Long electionId, Long voteId, List<VoteBox> voteBoxes) {
//    // PROS_CONS 투표 처리
//    Long countPros = voteBoxRepository.countByHadChosenTrueAndVoteId(voteId);
//    Long countCons = voteBoxRepository.countByHadChosenFalseAndVoteId(voteId);
//    Long prosRatio = calculateProsRatio(countPros, countCons);
//    Long consRatio = calculateConsRatio(countPros, countCons);
//
//    saveCandCount(electionId, voteId, voteBoxes.get(0).getCandidateId(), prosRatio, consRatio, countPros > countCons);
//  }
//
//  public void processCandCount(VoteType voteType, Long electionId, Long voteId, List<VoteBox> voteBoxes, List<Double> avgList) {
//    for (VoteBox voteBox : voteBoxes) {
//      double sumValue;
//      double avg = 0.0;
//
//      switch (voteType) {
//        case CHOICE:
//          sumValue = voteBoxRepository.sumChoicesByCandidateId(voteBox.getCandidateId());
//          break;
//        case SCORE:
//          sumValue = voteBoxRepository.sumScoresByCandidateId(voteBox.getCandidateId());
//          break;
//        case PREFERENCE:
//          sumValue = voteBoxRepository.sumRanksByCandidateId(voteBox.getCandidateId());
//          break;
//        default:
//          throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_NOT_VALID);
//      }
//
//      Double usersNum = voteBoxRepository.countUsersPhonesByCandidateId(voteBox.getCandidateId());
//
//      if (usersNum > 0) {
//        avg = sumValue / usersNum;
//      }
//
//      avgList.add(avg);
//
//      saveCandCount(electionId, voteId, voteBox.getCandidateId(), voteType, avg);
//    }
//  }
//  private void saveCandCount(Long electionId, Long voteId, Long candidateId, VoteType voteType, Double avg) {
//    CandCount candCount = new CandCount();
//    candCount.setElectionId(electionId);
//    candCount.setVoteId(voteId);
//    candCount.setCandidateId(candidateId);
//
//    switch (voteType) {
//      case CHOICE:
//        candCount.setChoicesAvg(avg);
//        break;
//      case SCORE:
//        candCount.setScoresAvg(avg);
//        break;
//      case PREFERENCE:
//        candCount.setRanksAvg(avg);
//        break;
//      default:
//        throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_NOT_VALID);
//    }
//
//    candCountRepository.save(candCount);
//  }
//
//  private void saveCandCount(Long electionId, Long voteId, Long candidateId, Long prosRatio, Long consRatio, boolean result) {
//    CandCount candCount = new CandCount();
//    candCount.setElectionId(electionId);
//    candCount.setVoteId(voteId);
//    candCount.setCandidateId(candidateId);
//    candCount.setProsRatio(prosRatio);
//    candCount.setConsRatio(consRatio);
//    candCount.setResult(result);
//
//    candCountRepository.save(candCount);
//  }
//
//  private Long calculateProsRatio(Long countPros, Long countCons) {
//    return (countPros * 100) / (countPros + countCons);
//  }
//
//  private Long calculateConsRatio(Long countPros, Long countCons) {
//    return (countCons * 100) / (countPros + countCons);
//  }
//
//}
