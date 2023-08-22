package com.project.voting.service.users;

import com.project.voting.dto.users.UsersDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UsersService {

  List<UsersDto> detailList(Long electionId);
}
