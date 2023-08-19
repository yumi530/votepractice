package com.project.voting.service.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.admin.AdminRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.dto.election.ElectionDto;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.project.voting.dto.vote.VoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @Transactional
    public Election addElectionAndVote(ElectionDto electionDto,
                                       @AuthenticationPrincipal Admin admin, MultipartFile file) throws IOException {

        String fileName = "";

        if (file != null && !file.isEmpty()) {
            if (isExcelFile(file)) {
                String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
                UUID uuid = UUID.randomUUID();

                String originalFilename = file.getOriginalFilename();
                fileName = uuid + "_" + originalFilename;
                File saveFile = new File(projectPath, fileName);
                file.transferTo(saveFile);
            } else {
                throw new IllegalArgumentException("엑셀 파일이 아닙니다.");
            }
        }

        Admin adminId = adminRepository.findById(admin.getUsername()).get();

    Election election = Election.builder()
            .electionTitle(electionDto.getElectionTitle())
            .groupName(electionDto.getGroupName())
            .electionStartDt(electionDto.getElectionStartDt())
            .electionEndDt(electionDto.getElectionEndDt())
            .filename(fileName)
            .filepath("/files/" + fileName)
            .admin(adminId)
            .build();

        electionRepository.save(election);

    List<VoteDto> voteList = electionDto.getVotes();

        for(
    VoteDto dto :voteList)

    {

        Vote vote = Vote.builder()
                .voteTitle(dto.getVoteTitle())
                .candidateName(dto.getCandidateName())
                .candidateInfo(dto.getCandidateInfo())
                .election(election)
                .build();

        voteRepository.save(vote);
    }

        return election;
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
    public Election detail(ElectionDto electionDto) {
        Election election = electionRepository.findById(electionDto.getElectionId()).get();
        return election;
    }

    private boolean isExcelFile(MultipartFile file) {
        String[] allowedExtensions = { "xls", "xlsx" };

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        return Arrays.asList(allowedExtensions).contains(fileExtension);
    }


  /*
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
*/

}

