package com.project.voting.service.vote;

import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.dto.vote.VoteDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final ElectionRepository electionRepository;




    @Override
    public void deleteVote(Long voteId) {
        voteRepository.deleteById(voteId);
    }

//    public List<Vote> detail(Long voteId) {
//        List<Vote> votes = voteRepository.findAllById(voteId);
////        voteRepository.save(vote);
//        return votes;
//    }


    @Override
    public Vote save(VoteDto voteDto) {
        electionRepository.findById(voteDto.getElectionId()).ifPresent(voteDto::setElection);
        Vote vote = Vote.toEntity(voteDto);
        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> detail(List<Long> voteIds) {
        List<Vote> voteList = voteRepository.findAllById(voteIds);
        return voteList;
    }
}
