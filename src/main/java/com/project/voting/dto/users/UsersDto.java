package com.project.voting.dto.users;

import com.project.voting.domain.election.Election;
import com.project.voting.domain.users.Users;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private String usersPhone;
    private String usersName;
    private Long electionId;

    public static UsersDto of(Users users){
        return UsersDto.builder()
          .usersPhone(users.getUsersPhone())
          .usersName(users.getUsersName())
          .electionId(users.getElection().getElectionId())
          .build();
    }
    public static List<UsersDto> of(List<Users> usersList) {
        if (usersList == null) {
            return null;
        }
        List<UsersDto> usersDtoList = new ArrayList<>();
        for (Users users : usersList) {
            usersDtoList.add(of(users));
        }
        return usersDtoList;
    }

}
