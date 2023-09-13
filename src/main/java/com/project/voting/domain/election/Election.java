package com.project.voting.domain.election;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.voting.domain.admin.Admin;

import com.project.voting.domain.users.Users;
import java.util.ArrayList;
import javax.persistence.*;

import com.project.voting.domain.vote.Vote;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Election {

  @Id
  @GeneratedValue
  private Long electionId;
  private String electionTitle;
  private String groupName;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime electionStartDt;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime electionEndDt;

//  @Column
//  private String usersPhone;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private Admin admin;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "electionId")
  private List<Vote> votes;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "electionId")
  private List<Users> users;


  @Builder
  public Election(Long electionId, String electionTitle, String groupName,
    LocalDateTime electionStartDt, LocalDateTime electionEndDt, Admin admin, List<Vote> votes) {
    this.electionId = electionId;
    this.electionTitle = electionTitle;
    this.groupName = groupName;
    this.electionStartDt = electionStartDt;
    this.electionEndDt = electionEndDt;
    this.votes = votes;
    this.admin = admin;

  }

}

