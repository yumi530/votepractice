package com.project.voting.domain.users;

import com.project.voting.domain.BaseEntity;

import javax.persistence.*;

import com.project.voting.domain.election.Election;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
public class Users extends BaseEntity{
//  @EmbeddedId
//  private UsersElectionKey id;

  @Id
  private String usersPhone;

  private String usersName;

//  private boolean isCompleted;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "election_election_id",referencedColumnName = "electionId")
  private Election election;

  @Builder
  public Users(String usersPhone, String usersName, Election election){
    this.usersPhone = usersPhone;
    this.usersName = usersName;
    this.election = election;
//    this.isCompleted = isCompleted;

  }

}
