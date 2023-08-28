package com.project.voting.service.count;

import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.project.voting.dto.election.ElectionDto;
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
        Map<String, Boolean> countSessionsMap = votedSessionsMap.getOrDefault(voteId, new HashMap<>());
        return countSessionsMap.getOrDefault(sessionId, false);

    }

    @Override
    public void confirmVoted(HttpSession session, Long voteId) {
        String sessionId = session.getId();
        Map<String, Boolean> countSessionsMap = votedSessionsMap.getOrDefault(voteId, new HashMap<>());
        countSessionsMap.put(sessionId, true);
        votedSessionsMap.put(voteId, countSessionsMap);
    }

    @Override
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public Vote countVotesResult(Long voteId, Long electionId) {

        Election election = electionRepository.findById(electionId).get();
        LocalDateTime now = LocalDateTime.now();

        if (election.getElectionEndDt().isAfter(now)) {
            throw new RuntimeException("선거 종료일 전에는 개표할 수 없습니다.");
        }

        Long countIds = countRepository.countAllByVoteVoteId(voteId);
        Long countPros = countRepository.countByIsAgreedTrueAndVoteVoteId(voteId);
        Long countCons = countRepository.countByIsAgreedFalseAndVoteVoteId(voteId);

        double prosRatio = pros(countIds, countPros);
        double consRatio = cons(countIds, countCons);

        Vote votes = voteRepository.findById(voteId).get();

        votes.setResult(prosCons(countIds, countPros));
        votes.setProsRatio(prosRatio);
        votes.setConsRatio(consRatio);

        return voteRepository.save(votes);
    }

    @Override
    public Vote countVotesResultConfirm(Long voteId) {
        Vote votes = voteRepository.findById(voteId).get();
        return voteRepository.save(votes);
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

    private Count toCount(boolean isAgreed, Vote vote) {
        return Count.builder()
                .isAgreed(isAgreed)
                .vote(vote)
//                .hadVoted(hadVoted)
                .build();
    }
}
