package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

  private final CountRepository countRepository;
  private final VoteRepository voteRepository;
  private final UsersRepository usersRepository;

  @Override
  public Count save(boolean isAgreed, Long voteId, boolean hadVoted) {
    Vote vote = voteRepository.findById(voteId).orElse(null);
    if (vote == null) {
      throw new RuntimeException("투표 정보를 찾을 수 없습니다.");
    }
    Count count = toCount(isAgreed, vote, hadVoted);
    return countRepository.save(count);
  }

  @Override
  public Vote countVotes(Long voteId) {

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

    votes.setResult(prosCons(countUsers, countPros));
    votes.setProsRatio(pros(countUsers, countPros));
    votes.setConsRatio(cons(countUsers, countPros));

    return voteRepository.save(votes);
  }

  @Override
  public Vote countVotesResultConfirm(Long voteId) {
    Vote votes = voteRepository.findById(voteId).get();
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
