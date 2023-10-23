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
import java.util.ArrayList;
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
  public void countVotesResult(Long voteId, Long electionId, VoteType voteType) {

    Election election = electionRepository.findById(electionId).get();

    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
    }

    List<VoteBox> voteBoxes = voteBoxRepository.findAllByVoteId(voteId);
    TreeSet<Double> avgList = new TreeSet<>(Collections.reverseOrder());

    if (voteType.equals(VoteType.PROS_CONS)) {
      Long countPros = voteBoxRepository.countByHadChosenTrueAndVoteId(voteId);
      Long countCons = voteBoxRepository.countByHadChosenFalseAndVoteId(voteId);
      Long prosRatio = countPros / (countPros + countCons) * 100;
      Long consRatio = countCons / (countPros + countCons) * 100;

      CandCount candCount = new CandCount();
      candCount.setElectionId(electionId);
      candCount.setVoteId(voteId);
      candCount.setCandidateId(voteBoxes.get(0).getCandidateId());

      candCount.setResult(countPros > countCons ? true : false);
      candCount.setProsRatio(prosRatio);
      candCount.setConsRatio(consRatio);

      candCountRepository.save(candCount);
    }

    if (voteType.equals(VoteType.CHOICE)) {
      for (VoteBox voteBox : voteBoxes) {
        Integer sumChoices = voteBoxRepository.sumChoicesByCandidateId(voteBox.getCandidateId());
        Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(voteBox.getCandidateId());

        double avg = (double) sumChoices / (double) usersPhones;
        avgList.add(avg);

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(voteBox.getCandidateId());
        candCount.setChoicesAvg(avg);

        candCountRepository.save(candCount);
      }
    }

    if (voteType.equals(VoteType.SCORE)) {
      for (VoteBox voteBox : voteBoxes) {
        Integer sumScores = voteBoxRepository.sumScoresByCandidateId(voteBox.getCandidateId());
        Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(voteBox.getCandidateId());

        double avg = (double) sumScores / (double) usersPhones;
        avgList.add(avg);

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(voteBox.getCandidateId());
        candCount.setScoresAvg(avg);

        candCountRepository.save(candCount);
      }
    }

    if (voteType.equals(VoteType.PREFERENCE)) {
      for (VoteBox voteBox : voteBoxes) {
        Integer sumRanks = voteBoxRepository.sumRanksByCandidateId(voteBox.getCandidateId());
        Integer usersPhones = voteBoxRepository.countUsersPhonesByCandidateId(voteBox.getCandidateId());

        double avg = (double) sumRanks / (double) usersPhones;
        avgList.add(avg);

        CandCount candCount = new CandCount();
        candCount.setElectionId(electionId);
        candCount.setVoteId(voteId);
        candCount.setCandidateId(voteBox.getCandidateId());
        candCount.setRanksAvg(avg);

        candCountRepository.save(candCount);
      }
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

}
