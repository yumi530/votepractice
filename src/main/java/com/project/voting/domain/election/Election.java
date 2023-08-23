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
  private LocalDateTime electionStartDt;
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
  public Election(Long electionId, String electionTitle, String groupName, LocalDateTime electionStartDt, LocalDateTime electionEndDt, List<Vote>votes, Admin admin, String usersPhone) {
    this.electionId = electionId;
    this.electionTitle = electionTitle;
    this.groupName = groupName;
    this.electionStartDt = electionStartDt;
    this.electionEndDt = electionEndDt;
    this.votes = votes;
    this.admin =admin;
    this.usersPhone = usersPhone;
  }

/*
  public static Election toEntity(ElectionDto electionDto) {
    return Election.builder()
      .electionId(electionDto.getElectionId())
      .electionTitle(electionDto.getElectionTitle())
      .groupName(electionDto.getGroupName())
      .electionStartDt(electionDto.getElectionStartDt())
      .electionEndDt(electionDto.getElectionEndDt())
      .votes(electionDto.getVotes())

      .build();
  }
 */
}
