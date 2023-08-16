package com.project.voting.service.vote;


import com.project.voting.domain.election.Election;
import com.project.voting.domain.vote.Vote;
import com.project.voting.dto.election.ElectionDto;
import com.project.voting.dto.vote.VoteDto;

public interface VoteService {

    //  Vote addProCons(VoteDto voteDto);
//  Vote addPreference(VoteDto voteDto);

    Vote addVote(VoteDto voteDto);

    void deleteVote(Long voteId);

    Vote detail(Long voteId);

//    Vote save(VoteDto voteDto);

  Vote save(VoteDto voteDto);

//    Vote save(VoteDto voteDto, Election election);
}
