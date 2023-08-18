package com.project.voting.service.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.admin.AdminRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.dto.election.ElectionDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

  private final ElectionRepository electionRepository;
  private final VoteRepository voteRepository;
  private final AdminRepository adminRepository;


  @Override
  public Page<Election> getElectionList(Pageable pageable) {
    return electionRepository.findAll(pageable);
  }

  @Override
  public Election createdElection() {
    Election election = new Election();
    election.setElectionId(election.getElectionId());
    return electionRepository.save(election);
  }


  @Override
  public Election addElection(ElectionDto electionDto,
    @AuthenticationPrincipal Admin admin) {
    Admin adminId = adminRepository.findById(admin.getUsername()).get();
    Election electionId = electionRepository.findById(createdElection().getElectionId()).get();


    Election addElection = Election.builder()
      .electionId(electionId.getElectionId())
      .electionTitle(electionDto.getElectionTitle())
      .groupName(electionDto.getGroupName())
      .electionStartDt(electionDto.getElectionStartDt())
      .electionEndDt(electionDto.getElectionEndDt())
      .votes(electionDto.getVotes())
      .admin(adminId)
      .build();
    return electionRepository.save(addElection);
  }

  @Override
  public void deleteElection(Long electionId) {
    electionRepository.deleteById(electionId);
  }

  @Override
  public Election countElection(Long electionId) {
    return null;
  }

  @Override
  public Election detail(Long electionId) {
    Election election = electionRepository.findById(electionId).get();
//    electionRepository.save(election);
    return election;


  }

  @Override
  public Election save(ElectionDto electionDto) {
    Election election = Election.toEntity(electionDto);
    return electionRepository.save(election);
  }

  @Override
  public Election openedElection(ElectionDto electionDto) {
    Optional<Election> optionalElection = electionRepository.findById(electionDto.getElectionId());
    if (!optionalElection.isPresent()) {
      throw new RuntimeException("선거가 존재하지 않습니다.");
    }
    Election election = optionalElection.get();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime electionEndDt = LocalDateTime.parse(election.getElectionEndDt(), formatter);

    if (currentDateTime.isAfter(electionEndDt)) {
      return election;
    } else {
      throw new RuntimeException("선거 기간이 아직 종료되지 않았습니다.");
    }
  }


}

