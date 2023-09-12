package com.project.voting.domain.users;

import com.project.voting.domain.BaseEntity;

import javax.persistence.*;

import com.project.voting.domain.election.Election;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(UsersElectionKey.class)
public class Users {

    @Id
    private Long electionId;
    @Id
    private String usersPhone;

    private String usersName;

    private boolean usersCompleted;

    @Builder
    public Users(String usersPhone, String usersName, Long electionId, boolean usersCompleted) {
        this.usersPhone = usersPhone;
        this.usersName = usersName;
        this.electionId = electionId;
        this.usersCompleted = usersCompleted;

    }
}
