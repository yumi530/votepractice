package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

  private final CountRepository countRepository;
  private final VoteRepository voteRepository;
  private final UsersRepository usersRepository;
  private final ElectionRepository electionRepository;

  @Override
  public Count save(boolean isAgreed, Long voteId, boolean hadVoted) {
    Vote vote = voteRepository.findById(voteId).orElse(null);
    if (vote == null) {
      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
    }
//    Users optionalUsers = usersRepository.findById(users.getUsersPhone()).orElse(null);
//    if (optionalUsers == null) {
//      throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
//    }
//    if (optionalUsers.isCompleted()){
//      throw new RuntimeException("이미 투표를 완료한 사용자 입니다.");
//    }
//    Users completedUsers = Users.builder()
//      .isCompleted(true)
//      .build();
//    usersRepository.save(completedUsers);
//    Count optionalCount = countRepository.findById(voteId).orElse(null);
//    if (optionalCount == null) {
//      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
//    }
//    if (optionalCount.had)
    Count count = toCount(isAgreed, vote, hadVoted);
    return countRepository.save(count);
  }

//  @Override
//  public Users complete(Users users) {
//    Users optionalUsers = usersRepository.findById(users.getUsersPhone()).orElse(null);
//    if (optionalUsers == null) {
//      throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
//    }
//    if (optionalUsers.isCompleted()){
//      throw new RuntimeException("이미 투표를 완료한 사용자 입니다.");
//    }
//     Users completedUsers = Users.builder()
//      .isCompleted(true)
//      .build();
//    return usersRepository.save(completedUsers);
//  }

  @Override
  public Vote countVotes(Long voteId) {
//     voteRepository.findById(voteId)
//      .orElseThrow(() -> new RuntimeException("투표를 찾을 수 없습니다."));
    Optional<Vote> optionalVote = voteRepository.findById(voteId);
    if (!optionalVote.isPresent()) {
      throw new RuntimeException("투표가 조회되지 않습니다.");
    }
    Vote votes = optionalVote.get();
    return voteRepository.save(votes);
  }

  @Override
  public Vote countVotesResult(Long voteId, Long electionId) {

    Long countUsers = usersRepository.countAllByElectionElectionId(electionId);
    Long countPros = countRepository.countIsAgreedByVoteVoteId(voteId);

    Vote votes = voteRepository.findById(voteId).get();
//    votes.setVoteId(votes.getVoteId());
//    votes.setElection(votes.getElection());
//    votes.setVoteTitle(votes.getVoteTitle());
//    votes.setCandidateName(votes.getCandidateName());
//    votes.setCandidateInfo(votes.getCandidateInfo());
    votes.setResult(prosCons(countUsers, countPros));
    votes.setProsRatio(pros(countUsers, countPros));
    votes.setConsRatio(cons(countUsers, countPros));

//    Vote votes = Vote.builder()
//      .result(prosCons(countUsers, countPros))
//      .prosRatio(pros(countUsers, countPros))
//      .consRatio(cons(countUsers, countPros))
//      .build();
    return voteRepository.save(votes);
  }
  private boolean prosCons(Long countUsers, Long countPros) {
    if ((countUsers / 2) > countPros) {
      return true;
    }
    return false;
  }
  private double pros(Long countUsers, Long countPros) {
    return (countPros / countUsers);
  }
  private double cons(Long countUsers, Long countPros){
    return ((countUsers-countPros) / countUsers);
  }
  private Count toCount(boolean isAgreed, Vote vote, boolean hadVoted) {
    return Count.builder()
      .isAgreed(isAgreed)
      .vote(vote)
      .hadVoted(hadVoted)
      .build();
  }
}
