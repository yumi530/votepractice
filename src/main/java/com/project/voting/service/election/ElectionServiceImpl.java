package com.project.voting.service.election;


import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.admin.AdminRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.dto.election.ElectionDto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.*;

import com.project.voting.dto.vote.VoteDto;
import com.project.voting.vo.users.UsersVo;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

  private final ElectionRepository electionRepository;
  private final VoteRepository voteRepository;
  private final AdminRepository adminRepository;
  private final UsersRepository usersRepository;
//    private final ExcelUtil excelUtil;


  @Override
  public Page<Election> getElectionList(Pageable pageable) {
    return electionRepository.findAll(pageable);
  }


  @Override
  @Transactional
  public Election addElectionAndVote(ElectionDto electionDto,
    @AuthenticationPrincipal Admin admin, MultipartFile file, List<String> voteTypes)
    throws IOException {

    Optional<Admin> optionalAdmin = adminRepository.findById(admin.getUsername());
    Admin adminId = optionalAdmin.orElseThrow(() -> new RuntimeException("관리자 정보를 찾을 수 없습니다."));

    LocalDateTime now = LocalDateTime.now();

    LocalDateTime electionStartDt = electionDto.getElectionStartDt();
    LocalDateTime electionEndDt = electionDto.getElectionEndDt();

    if (electionStartDt.isBefore(now)) {
      throw new RuntimeException("시작일시는 현재 시각 이후로 설정해야 합니다.");
    }

    if (electionEndDt.isBefore(electionStartDt)) {
      throw new RuntimeException("종료일시는 시작일시 이후로 설정해야 합니다.");
    }

    Election election = Election.builder()
      .electionTitle(electionDto.getElectionTitle())
      .groupName(electionDto.getGroupName())
      .electionStartDt(electionStartDt)
      .electionEndDt(electionEndDt)
      .admin(adminId)
      .build();

    electionRepository.save(election);

    List<VoteDto> voteList = electionDto.getVotes();

    for (int i = 0; i < voteList.size(); i++) {
      String voteType = voteTypes.get(i);
      VoteDto dto = voteList.get(i);

      if ("PROS_CONS".equals(voteType)) {

        Vote vote = Vote.builder()
          .voteTitle(dto.getVoteTitle())
          .voteType(VoteType.PROS_CONS)
          .election(election)
          .candidateName(dto.getCandidateName())
          .candidateInfo(dto.getCandidateInfo())
          .build();
        voteRepository.save(vote);

      } else if ("CHOICE".equals(voteType)) {

        List<String> candidateNames = dto.getCandidateNames();
        List<String> candidateInfos = dto.getCandidateInfos();

        if (candidateNames.size() >= 2) {

          List<Vote> votesList = new ArrayList<>();

          Vote vote = Vote.builder()
            .voteTitle(dto.getVoteTitle())
            .voteType(VoteType.PROS_CONS)
            .election(election)
            .build();
          voteRepository.save(vote);

          Long voteId = voteRepository.findById(vote.getVoteId()).get().getVoteId();

          for (int j = 0; j < candidateNames.size(); j++) {
            String candidateName = candidateNames.get(j);
            String candidateInfo = candidateInfos.get(j);

            vote.setVoteId(voteId);
            vote.setCandidateName(candidateName);
            vote.setCandidateInfo(candidateInfo);

            votesList.add(vote);
          }

          voteRepository.saveAll(votesList);


      } else {
        throw new RuntimeException("후보자를 2명 이상 등록해주세요.");
      }
    }else if ("SCORE".equals(voteType)) {

      List<String> candidateNames = dto.getCandidateNames();
      List<String> candidateInfos = dto.getCandidateInfos();

      if (candidateNames.size() >= 2) {

        for (int j = 0; j < candidateNames.size(); j++) {
          String candidateName = candidateNames.get(j);
          String candidateInfo = candidateInfos.get(j);
          Vote vote = Vote.builder()
            .voteTitle(dto.getVoteTitle())
            .voteType(VoteType.SCORE)
            .election(election)
            .candidateName(candidateName)
            .candidateInfo(candidateInfo)
            .build();
          voteRepository.save(vote);
        }
      } else {
        throw new RuntimeException("후보자를 2명 이상 등록해주세요.");
      }
    } else if ("PREFERENCE".equals(voteType)) {

      List<String> candidateNames = dto.getCandidateNames();
      List<String> candidateInfos = dto.getCandidateInfos();

      if (candidateNames.size() >= 3) {

        for (int j = 0; j < candidateNames.size(); j++) {
          String candidateName = candidateNames.get(j);
          String candidateInfo = candidateInfos.get(j);
          Vote vote = Vote.builder()
            .voteTitle(dto.getVoteTitle())
            .voteType(VoteType.PREFERENCE)
            .election(election)
            .candidateName(candidateName)
            .candidateInfo(candidateInfo)
            .build();

          voteRepository.save(vote);
        }
      }
    } else {
      throw new RuntimeException("후보자를 3명 이상 등록해주세요.");
    }
  }

  String fileName = "";

  File fileInput = null;
    if(file !=null&&!file.isEmpty())

  {
    String filePath =
      System.getProperty("user.dir") + "\\src\\main\\resources\\statics\\files";
    fileName = file.getOriginalFilename();
    fileInput = new File(filePath, fileName);
    file.transferTo(fileInput);

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileInput))) {

      bufferedReader.readLine();

      List<Map<String, String>> listMap = bufferedReader.lines()
        .map(line -> {
          String[] parts = line.split(",");
          Map<String, String> map = new HashMap<>();
          map.put("0", parts[0]);
          map.put("1", parts[1]);
          return map;
        })
        .collect(Collectors.toList());

      List<UsersVo> listUser = new ArrayList<>();

      for (Map<String, String> map : listMap) {
        UsersVo userInfo = UsersVo.builder()
          .usersPhone(map.get("0"))
          .usersName(map.get("1"))
          .build();
        listUser.add(userInfo);
      }

      for (UsersVo oneUsersVo : listUser) {
        Users users = Users.builder()
          .usersPhone(oneUsersVo.getUsersPhone())
          .usersName(oneUsersVo.getUsersName())
          .election(election)
          .build();
        usersRepository.save(users);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  boolean fileDeleted = fileInput.delete();

  {
    if (fileDeleted) {
      System.out.println("삭제 완료");
    } else {
      throw new RuntimeException("삭제 안됨..........");
    }
  }
    return election;
}

  @Override
  public void deleteElection(Long electionId) {
    electionRepository.deleteById(electionId);
  }

  @Override
  public Election detail(Long electionId) {
    Election election = electionRepository.findById(electionId).get();
    return election;
  }

      /*    poi 라이브러리 사용한 경우

    if (file != null && !file.isEmpty()) {
      if (isExcelFile(file)) {
        List<UsersVo> listUser = new ArrayList<>();
        List<Map<String, String>> listMap = excelUtil.getListData(file, 1, 1);

        for (Map<String, String> map : listMap) {
          UsersVo userInfo = UsersVo.builder()
            .usersPhone(map.get("0"))
            .usersName(map.get("1"))
            .build();
          listUser.add(userInfo);
        }

        for (UsersVo oneUsersVo : listUser) {
          Users users = Users.builder()
            .usersPhone(oneUsersVo.getUsersPhone())
            .usersName(oneUsersVo.getUsersName())
            .election(election)
            .build();
          usersRepository.save(users);
        }
      } else {
        throw new IllegalArgumentException("엑셀 파일이 아닙니다.");
      }
    }
      return election;
    }

      private boolean isExcelFile (MultipartFile file){
        String[] allowedExtensions = {"xls", "xlsx"};

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
          .toLowerCase();

        return Arrays.asList(allowedExtensions).contains(fileExtension);
      }
*/
}

