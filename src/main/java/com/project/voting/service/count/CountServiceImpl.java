package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;

import com.project.voting.domain.vote.VoteType;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

  private final CountRepository countRepository;
  private final VoteRepository voteRepository;
  private final ElectionRepository electionRepository;
  private final Map<Long, Map<String, Boolean>> votedSessionsMap = new HashMap<>();


  @Override
  public Count save(boolean isAgreed, Long voteId) {
    Vote vote = voteRepository.findById(voteId).orElse(null);
    if (vote == null) {
      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
    }
//        Count count = toCount(isAgreed, vote, hadVoted);
//        return countRepository.save(count);

    Count count = toCount(isAgreed, vote);
    return countRepository.save(count);
  }

  @Override
  public boolean hadVoted(HttpSession session, Long voteId) {

    String sessionId = session.getId();
    Map<String, Boolean> countSessionsMap = votedSessionsMap.getOrDefault(voteId,
      new HashMap<>());
    return countSessionsMap.getOrDefault(sessionId, false);

  }

  @Override
  public void confirmVoted(HttpSession session, Long voteId) {
    String sessionId = session.getId();
    Map<String, Boolean> countSessionsMap = votedSessionsMap.getOrDefault(voteId,
      new HashMap<>());
    countSessionsMap.put(sessionId, true);
    votedSessionsMap.put(voteId, countSessionsMap);
  }

//  @Override
//  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//  public Vote countVotesResult(Long voteId, Long electionId, VoteType voteType,
//    String candidateName) {
//
//    Election election = electionRepository.findById(electionId).get();
//    LocalDateTime now = LocalDateTime.now();
//
//    if (election.getElectionEndDt().isAfter(now)) {
//      throw new RuntimeException("선거 종료일 전에는 개표할 수 없습니다.");
//    }
//
//    if (voteType == VoteType.PROS_CONS) {
//
//      Long countIds = countRepository.countAllByVoteVoteId(voteId);
//      Long countPros = countRepository.countByVoteTypeAndIsAgreedTrueAndVoteVoteId(
//        VoteType.PROS_CONS, voteId);
//      Long countCons = countRepository.countByVoteTypeAndIsAgreedFalseAndVoteVoteId(
//        VoteType.PROS_CONS, voteId);
//
//      double prosRatio = pros(countIds, countPros);
//      double consRatio = cons(countIds, countCons);
//
//      Vote votes = voteRepository.findById(voteId).get();
//
//      votes.setResult(prosCons(countIds, countPros));
//      votes.setProsRatio(prosRatio);
//      votes.setConsRatio(consRatio);
//
//      return voteRepository.save(votes);
//
//    } else if (voteType == VoteType.CHOICE) {
//
//      Long countIds = countRepository.countAllByVoteVoteId(voteId);
//      Long countChosen = countRepository.countByVoteTypeAndIsAgreedTrueAndVoteVoteId(
//        VoteType.CHOICE, voteId);
//      Long countUnChosen = countRepository.countByVoteTypeAndIsAgreedFalseAndVoteVoteId(
//        VoteType.CHOICE, voteId);
//
//      double chosenRatio = chosen(countIds, countChosen);
//      double unChosenRatio = unChosen(countIds, countUnChosen);
//
//      Vote votes = voteRepository.findById(voteId).get();
//
//      votes.setResult(prosCons(countIds, countChosen));
//      votes.setProsRatio(chosenRatio);
//      votes.setConsRatio(unChosenRatio);
//
//      return voteRepository.save(votes);
//
//    } else if (voteType == VoteType.SCORE) {
//
//      Long countIds = countRepository.countAllByVoteVoteId(voteId);
//      Long sumScore = countRepository.sumScoresByVoteTypeAndVoteVoteIdAndCandidateName(voteId,
//        VoteType.SCORE, candidateName);
//
//      Vote votes = voteRepository.findById(voteId).get();
//
//      votes.setScores(averageScores(countIds, sumScore));
//
//      List<Vote> allVotes = voteRepository.findAllById(Collections.singleton(voteId));
//
//      votes.setRanks(getRanks(allVotes, candidateName));
//
//      return voteRepository.save(votes);
//
//
//    }
//
//    return null;
//  }



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

  private double chosen(Long countIds, Long countChosen) {
    return ((double) countChosen / countIds) * 100.0;
  }

  private double unChosen(Long countIds, Long countUnChosen) {
    return ((double) countUnChosen / countIds) * 100.0;
  }

  private double averageScores(Long countIds, Long sumScore) {
    return ((double) sumScore / countIds);
  }

  private int getRanks(List<Vote> votes, String candidateName) {
    List<Vote> candidateVotes = votes.stream()
      .filter(vote -> candidateName.equals(vote.getCandidateName()))
      .collect(Collectors.toList());

    candidateVotes.sort(Comparator.comparing(Vote::getScores).reversed());

    int rank = 1;
    for (Vote vote : candidateVotes) {
      vote.setRanks(rank);
      rank++;
    }
    return rank;
  }






  private Count toCount(boolean isAgreed, Vote vote) {
    return Count.builder()
      .isAgreed(isAgreed)
      .vote(vote)
//                .hadVoted(hadVoted)
      .build();
  }
}
