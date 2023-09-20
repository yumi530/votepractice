package com.project.voting.service.cand_count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.domain.voteBox.VoteBox;
import com.project.voting.domain.voteBox.VoteBoxRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
  public CandCount countVotesResult(Long voteId, Long electionId, Long candidateId, String usersPhone,
    VoteType voteType) {
    Election election = electionRepository.findById(electionId).get();
    LocalDateTime now = LocalDateTime.now();

    if (election.getElectionEndDt().isAfter(now)) {
      throw new RuntimeException("선거 종료일 전에는 개표할 수 없습니다.");
    }

    if (voteType == VoteType.PROS_CONS) {

      Long countIds = voteBoxRepository.countAllByVoteId(voteId);
      Long countPros = voteBoxRepository.countByIsAgreedTrueAndCandidateId(candidateId);
      Long countCons = voteBoxRepository.countByIsAgreedFalseAndCandidateId(candidateId);

      double prosRatio = pros(countIds, countPros);
      double consRatio = cons(countIds, countCons);

      CandCount votes = candCountRepository.findById(voteId).get();

      votes.setResult(prosCons(countIds, countPros));
      votes.setProsRatio(prosRatio);
      votes.setConsRatio(consRatio);

      return candCountRepository.save(votes);

    } else if (voteType == VoteType.CHOICE) {

      Long countIds = voteBoxRepository.countAllByVoteId(voteId);
      Integer sumChoices = Integer.valueOf(voteBoxRepository.sumChoicesByCandidateId(candidateId));

      CandCount votes = candCountRepository.findById(voteId).get();
      votes.setChoicesAvg(averageChoices(countIds, sumChoices));

      List<VoteBox> allVotes = voteBoxRepository.findAllById(Collections.singleton(voteId));
      votes.setTotalRank(getRanks(allVotes, candidateId));

      return candCountRepository.save(votes);

    } else if (voteType == VoteType.SCORE) {

      Long countIds = voteBoxRepository.countAllByVoteId(voteId);
      Integer sumScore = voteBoxRepository.sumScoresByCandidateId(candidateId);

      CandCount votes = candCountRepository.findById(voteId).get();
      votes.setScoresAvg(averageScores(countIds, sumScore));

      List<VoteBox> allVotes = voteBoxRepository.findAllById(Collections.singleton(voteId));
      votes.setTotalRank(getRanks(allVotes, candidateId));

      return candCountRepository.save(votes);

    } else if (voteType == VoteType.PREFERENCE) {

      Long countIds = voteBoxRepository.countAllByVoteId(voteId);
      Integer sumRanks = voteBoxRepository.sumRanksByCandidateId(candidateId);

      CandCount votes = candCountRepository.findById(voteId).get();
      votes.setRanksAvg(averageScores(countIds, sumRanks));

      List<VoteBox> allVotes = voteBoxRepository.findAllById(Collections.singleton(voteId));
      votes.setTotalRank(getRanks(allVotes, candidateId));

      return candCountRepository.save(votes);
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

  private double averageScores(Long countIds, Integer sumScore) {
    return ((double) sumScore / countIds);
  }

  private double averageChoices(Long countIds, Integer sumChoices) {
    return ((double) sumChoices / countIds);
  }
  private double averageRanks(Long countIds, Integer sumRanks) {
    return ((double) sumRanks / countIds);
  }

  private int getRanks(List<VoteBox> votes, Long candidateId) {

    List<VoteBox> candidateVotes = votes.stream()
      .filter(vote -> candidateId.equals(vote.getCandidateId()))
      .collect(Collectors.toList());

    candidateVotes.sort(Comparator.comparing(VoteBox::getScores).reversed());

    int rank = 1;
    for (VoteBox voteBox : candidateVotes) {
      voteBox.setRanks(rank);
      rank++;
    }
    return rank - 1;
  }

}
