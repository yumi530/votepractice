package com.project.voting.domain.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class UsersElectionKey implements Serializable {
    private String usersPhone;
    private Long electionId;
}
