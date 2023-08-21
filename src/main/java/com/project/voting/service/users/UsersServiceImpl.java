package com.project.voting.service.users;

import com.project.voting.domain.election.ElectionRepository;
import com.project.voting.domain.users.Users;
import com.project.voting.domain.users.UsersRepository;
import com.project.voting.dto.users.UsersDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ElectionRepository electionRepository;
    private final String uploadedFolderPath = "/src/main/resources/static/files";

//
//    public boolean compareInputWithExcelData(MultipartFile file, String tel, UsersDto usersDto) throws IOException {
//        try (InputStream inputStream = file.getInputStream()) {
//            Workbook workbook = WorkbookFactory.create(inputStream);
//
//            Sheet sheet = workbook.getSheetAt(0);
//
//            for (Row row : sheet) {
//                Cell cell = row.getCell(0);
//
//                if (cell != null) {
//                    String excelData = cell.getStringCellValue();
//                    if (excelData.equals(tel)) {
//                        Optional<Users> optionalUsers = usersRepository.findById(usersDto.getUsersPhone());
//                        if (!optionalUsers.isPresent()) {
//                            throw new RuntimeException("전화번호가 없습니다.");
//                        }
//                        Users user = optionalUsers.get();
//                        usersRepository.save(user);
//                    }
//                }
//            }
//        }
//        return true;
//    }
}


