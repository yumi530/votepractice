package com.project.voting.domain.candidate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.voting.domain.users.Users;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(CandIdKey.class)
public class Candidate {

  @Id
  private Long electionId;

  @Id
  private Long voteId;

  @Id
  private Long candidateId;

  private String candidateName;
  private String candidateInfo;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "electionId" , insertable = false, updatable = false)
  @JoinColumn(name = "usersPhone", insertable = false, updatable = false)
  private Users users;

  @Column
  private String usersPhone;

  @Builder
  public Candidate(Long electionId, Long voteId, Long candidateId, String candidateName, String candidateInfo, Users users, String usersPhone) {
    this.electionId = electionId;
    this.voteId = voteId;
    this.candidateId = candidateId;
    this.candidateName = candidateName;
    this.candidateInfo = candidateInfo;
    this.users = users;
    this.usersPhone = usersPhone;
  }


}
