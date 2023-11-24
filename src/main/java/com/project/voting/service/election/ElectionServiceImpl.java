package com.project.voting.service.election;


import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.admin.AdminRepository;
import com.project.voting.domain.candidate.Candidate;
import com.project.voting.domain.candidate.CandidateRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.domain.vote.VoteType;
import com.project.voting.dto.election.ElectionDto;

import com.project.voting.exception.admin.AdminCustomException;
import com.project.voting.exception.admin.AdminErrorCode;
import com.project.voting.exception.candidate.CandidateCustomException;
import com.project.voting.exception.candidate.CandidateErrorCode;
import com.project.voting.exception.election.ElectionCustomException;
import com.project.voting.exception.election.ElectionErrorCode;
import com.project.voting.exception.users.UsersCustomException;
import com.project.voting.exception.users.UsersErrorCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.*;

import com.project.voting.dto.vote.VoteDto;
import com.project.voting.exception.vote.VoteCustomException;
import com.project.voting.exception.vote.VoteErrorCode;
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
    private final CandidateRepository candidateRepository;
//    private final ExcelUtil excelUtil;


    @Override
    public Page<Election> getElectionList(Pageable pageable) {
        return electionRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Election addElectionAndVote(ElectionDto electionDto, @AuthenticationPrincipal Admin admin, MultipartFile file) throws IOException {

        Optional<Admin> optionalAdmin = adminRepository.findById(admin.getUsername());
        Admin adminId = optionalAdmin.orElseThrow(() -> new AdminCustomException(AdminErrorCode.ADMIN_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime electionStartDt = electionDto.getElectionStartDt();
        LocalDateTime electionEndDt = electionDto.getElectionEndDt();

        if (electionStartDt.isBefore(now)) {
            throw new ElectionCustomException(ElectionErrorCode.START_TIME_NOT_VALID);
        }

        if (electionEndDt.isBefore(electionStartDt)) {
            throw new ElectionCustomException(ElectionErrorCode.END_TIME_NOT_VALID);
        }

        Election election = Election.builder()
                .electionTitle(electionDto.getElectionTitle())
                .groupName(electionDto.getGroupName())
                .electionStartDt(electionStartDt)
                .electionEndDt(electionEndDt)
                .admin(adminId)
                .build();

        electionRepository.save(election);

        processVotes(election, electionDto.getVotes());

        processFile(file, election);

        return election;
    }

    private void processVotes(Election election, List<VoteDto> voteList) {
        for (VoteDto dto : voteList) {
            Long voteId = generateVoteId(election.getElectionId());

            switch (dto.getVoteType()) {
                case "PROS_CONS":
                case "CHOICE":
                case "SCORE":
                case "PREFERENCE":
                    processVoteAndCandidates(election, dto, voteId, VoteType.valueOf(dto.getVoteType()));
                    break;
                default:
                    throw new VoteCustomException(VoteErrorCode.VOTE_TYPE_NOT_FOUND);
            }
        }
    }

    private void processVoteAndCandidates(Election election, VoteDto dto, Long voteId, VoteType voteType) {
        Vote vote = Vote.builder()
                .electionId(election.getElectionId())
                .voteId(voteId)
                .voteTitle(dto.getVoteTitle())
                .voteType(voteType)
                .build();
        voteRepository.save(vote);

        List<String> candidateNames = dto.getCandidateNames();
        List<String> candidateInfos = dto.getCandidateInfos();
        List<Long> candidateIds = generateCandidateIds(election.getElectionId(), voteId, candidateNames);

        if (candidateNames.size() >= voteType.getMinCandidates()) {
            List<Candidate> candidates = new ArrayList<>();

            for (int j = 0; j < candidateNames.size(); j++) {
                String candidateName = candidateNames.get(j);
                String candidateInfo = candidateInfos.get(j);
                Long candidateId = candidateIds.get(j);

                Candidate candidate = Candidate.builder()
                        .electionId(election.getElectionId())
                        .voteId(vote.getVoteId())
                        .candidateId(candidateId)
                        .candidateName(candidateName)
                        .candidateInfo(candidateInfo)
                        .build();
                candidates.add(candidate);
            }
            candidateRepository.saveAll(candidates);
        } else {
            throw new CandidateCustomException(CandidateErrorCode.NUMBERS_OF_CANDIDATE_NOT_VALID);
        }
    }
    private void processFile(MultipartFile file, Election election) throws IOException {
        String fileName = "";
        File fileInput = null;
        if (file != null && !file.isEmpty()) {
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
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
                            .electionId(election.getElectionId())
                            .build();
                    usersRepository.save(users);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean fileDeleted = fileInput.delete();

        if (!fileDeleted) {
            throw new UsersCustomException(UsersErrorCode.USERS_FILE_NOT_DELETED);
        }
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

    @Override
    public Election detailElection(Long voteId) {

        Election election = electionRepository.findElectionIdByVotes_VoteId(voteId);

        if (LocalDateTime.now().isBefore(election.getElectionStartDt()))
            throw new ElectionCustomException(ElectionErrorCode.ELECTION_NOT_AVAILABLE);

        return election;
    }

    private Long generateVoteId(Long electionId) {
        return electionId * 1000 + System.currentTimeMillis();
    }

    private Long generateCandidateId(Long electionId, Long voteId) {
        return (electionId * 1000) + (voteId * 1000) + System.currentTimeMillis();
    }

    private List<Long> generateCandidateIds(Long electionId, Long voteId,
                                            List<String> candidateNames) {
        List<Long> candidateIds = new ArrayList<>();
        for (int i = 0; i < candidateNames.size(); i++) {
            long candidateId = (electionId * 1000) + (voteId * 1000) + System.currentTimeMillis() + i;
            candidateIds.add(candidateId);
        }
        return candidateIds;
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


