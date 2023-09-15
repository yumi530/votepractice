package com.project.voting.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.project.voting.domain.candidate.Candidate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(UsersElectionKey.class)
public class Users {

    @Id
    private String usersPhone;

    @Id
    private Long electionId;

    private String usersName;

    private boolean usersCompleted;

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<Candidate> candidates;

//    public void addCandidate(Candidate candidate) {
//        if (candidateId == null) {
//            candidateId = new ArrayList<>();
//        }
//        candidateId.add(candidate);
//        candidate.setUsers(this);
//    }



    @Builder
    public Users(String usersPhone, String usersName, Long electionId, boolean usersCompleted, List<Candidate> candidates) {
        this.usersPhone = usersPhone;
        this.usersName = usersName;
        this.electionId = electionId;
        this.usersCompleted = usersCompleted;
        this.candidates = candidates;

    }
}
