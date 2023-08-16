package com.project.voting.service.vote;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.dto.vote.VoteDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final ElectionRepository electionRepository;

    @Override
    public Vote addVote(VoteDto voteDto) {

        Vote addProCons = Vote.builder()
                .voteId(voteDto.getVoteId())
                .voteTitle(voteDto.getVoteTitle())
                .voteType(voteDto.getVoteType())
                .candidateName(voteDto.getCandidateName())
                .candidateInfo(voteDto.getCandidateInfo())
                .agreeYn(voteDto.isAgreeYn())
                .build();
        return voteRepository.save(addProCons);
    }


    @Override
    public void deleteVote(Long voteId) {
        voteRepository.deleteById(voteId);
    }

    @Override
    public Vote detail(Long votId) {
        Vote vote = voteRepository.findById(votId).get();
        voteRepository.save(vote);
        return vote;
    }


    @Override
    public Vote save(VoteDto voteDto) {
        electionRepository.findById(voteDto.getElectionId()).ifPresent(voteDto::setElection);
        Vote vote = Vote.toEntity(voteDto);
        return voteRepository.save(vote);
    }
}
