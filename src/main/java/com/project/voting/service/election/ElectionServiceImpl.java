package com.project.voting.service.election;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.admin.AdminRepository;
import com.project.voting.domain.election.Election;
import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.domain.vote.Vote;
import com.project.voting.domain.vote.VoteRepository;
import com.project.voting.dto.election.ElectionDto;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.*;

import com.project.voting.dto.vote.VoteDto;
import com.project.voting.util.ExcelUtil;
import com.project.voting.vo.users.UsersVo;
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
    private final ExcelUtil excelUtil;


    @Override
    public Page<Election> getElectionList(Pageable pageable) {
        return electionRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Election addElectionAndVote(ElectionDto electionDto,
                                       @AuthenticationPrincipal Admin admin, MultipartFile file) throws IOException {

        Optional<Admin> optionalAdmin = adminRepository.findById(admin.getUsername());
        Admin adminId = optionalAdmin.orElseThrow(() -> new RuntimeException("관리자 정보를 찾을 수 없습니다."));


        LocalDateTime now = LocalDateTime.now();

//        LocalDateTime electionStartDt = LocalDateTime.parse(electionDto.getElectionStartDt());
//        LocalDateTime electionEndDt = LocalDateTime.parse(electionDto.getElectionEndDt());
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
//                .electionStartDt(String.valueOf(electionStartDt))
//                .electionEndDt(String.valueOf(electionEndDt))
                .electionStartDt(electionStartDt)
                .electionEndDt(electionEndDt)
                .admin(adminId)
                .build();

        electionRepository.save(election);

        List<VoteDto> voteList = electionDto.getVotes();

        for (
                VoteDto dto : voteList) {

            Vote vote = Vote.builder()
                    .voteTitle(dto.getVoteTitle())
                    .candidateName(dto.getCandidateName())
                    .candidateInfo(dto.getCandidateInfo())
                    .election(election)
                    .build();

            voteRepository.save(vote);
        }
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

    @Override
    public void deleteElection(Long electionId) {
        electionRepository.deleteById(electionId);
    }


    @Override
    public Election detail(Long electionId) {
        Election election = electionRepository.findById(electionId).get();
        return election;
    }


    private boolean isExcelFile(MultipartFile file) {
        String[] allowedExtensions = {"xls", "xlsx"};

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
                .toLowerCase();

        return Arrays.asList(allowedExtensions).contains(fileExtension);
    }
}

