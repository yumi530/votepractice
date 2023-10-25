//package com.project.voting.service.cand_count;
//
//import com.project.voting.domain.cand_count.CandCount;
//import com.project.voting.domain.cand_count.CandCountRepository;
//import com.project.voting.domain.election.Election;
//import com.project.voting.domain.election.ElectionRepository;
//import com.project.voting.domain.vote.VoteType;
//import com.project.voting.domain.voteBox.VoteBox;
//import com.project.voting.domain.voteBox.VoteBoxRepository;
//import com.project.voting.exception.cand_count.CandCountCustomException;
//import com.project.voting.exception.cand_count.CandCountErrorCode;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CandCountServiceImpl implements CandCountService{
//
//  private final ElectionRepository electionRepository;
//  private final VoteBoxRepository voteBoxRepository;
//  private final CandCountRepository candCountRepository;
//  private final CandCountProcess candCountProcess;
//
//  @Override
//  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//  public void countVotesResult(Long voteId, Long electionId, VoteType voteType) {
//
//    Election election = electionRepository.findById(electionId).get();
//
//    LocalDateTime now = LocalDateTime.now();
//
//    if (election.getElectionEndDt().isAfter(now)) {
//      throw new CandCountCustomException(CandCountErrorCode.CAND_COUNT_TIME_NOT_VALID);
//    }
//
//    List<VoteBox> voteBoxes = voteBoxRepository.findAllByVoteId(voteId);
//
//    List<Double> avgList = new ArrayList<>();
//
//    if (voteType.equals(VoteType.PROS_CONS)) {
//     candCountProcess.processProsConsCandCount(electionId, voteId, voteBoxes);
//    }
//    else {
//      candCountProcess.processCandCount(voteType, electionId, voteId, voteBoxes, avgList);
//    }
//  }
//
//  @Override
//  public List<CandCount> details(Long voteId) {
//    return candCountRepository.findAllCandidateIdsByVoteId(voteId);
//  }
//
//  @Override
//  public CandCount detail(Long voteId) {
//    return candCountRepository.findCandidateIdByVoteId(voteId);
//  }
//
//  @Override
//  public VoteType getVoteType() {
//    return null;
//  }
//
//}
