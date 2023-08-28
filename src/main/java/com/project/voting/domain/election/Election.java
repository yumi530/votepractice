package com.project.voting.domain.election;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.voting.domain.BaseEntity;
import com.project.voting.domain.admin.Admin;

import com.project.voting.domain.users.Users;
import com.project.voting.dto.election.ElectionDto;

import javax.persistence.*;

import com.project.voting.domain.vote.Vote;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Election extends BaseEntity {

    @Id
    @GeneratedValue
    private Long electionId;
    private String electionTitle;
    private String groupName;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime electionStartDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime electionEndDt;

    @Column
    private String usersPhone;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    @JsonIgnore
    @OneToMany(mappedBy = "election")
    private List<Vote> votes = new ArrayList<>();


    @Builder
    public Election(Long electionId, String electionTitle, String groupName, LocalDateTime electionStartDt, LocalDateTime electionEndDt, List<Vote> votes, Admin admin, String usersPhone) {
        this.electionId = electionId;
        this.electionTitle = electionTitle;
        this.groupName = groupName;
        this.electionStartDt = electionStartDt;
        this.electionEndDt = electionEndDt;
        this.votes = votes;
        this.admin = admin;
        this.usersPhone = usersPhone;
    }
}
