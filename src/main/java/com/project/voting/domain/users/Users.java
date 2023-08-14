package com.project.voting.domain.users;

import com.project.voting.domain.BaseEntity;

import javax.persistence.*;

import com.project.voting.domain.election.Election;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter


public class Users extends BaseEntity{
  @Id
  private String usersPhone;

  private String usersName;
  private String code;

  @OneToOne(fetch = FetchType.LAZY)
  private Election election;

  @Builder
  public Users(String usersPhone, String usersName, String code){
    this.usersPhone = usersPhone;
    this.usersName = usersName;
    this.code = code;
  }

}
