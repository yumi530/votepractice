package com.project.voting.vo.users;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UsersVo {
    private String usersPhone;
    private String usersName;
    private Long electionId;

    @Builder
    public UsersVo(String usersPhone, String usersName, Long electionId){
        this.usersPhone = usersPhone;
        this.usersName = usersName;
        this.electionId = electionId;
    }
}