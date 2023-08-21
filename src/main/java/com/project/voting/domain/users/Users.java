package com.project.voting.domain.users;

import com.project.voting.domain.BaseEntity;

import javax.persistence.*;

import com.project.voting.domain.election.Election;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
public class Users extends BaseEntity{
  @Id
  private String usersPhone;

  private String usersName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "election_election_id")
  private Election election;

  @Builder
  public Users(String usersPhone, String usersName, Election election){
    this.usersPhone = usersPhone;
    this.usersName = usersName;
    this.election = election;

  }

}
