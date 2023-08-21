//package com.project.voting.domain.users;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Embeddable;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.SequenceGenerator;
//import java.io.Serializable;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Embeddable
//@EqualsAndHashCode
//@Data
//public class UsersElectionKey implements Serializable {
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_election_seq_generator")
//    @SequenceGenerator(name = "users_election_seq_generator", sequenceName = "users_election_seq", allocationSize = 1)
//    private Long electionId;
//    private Long sequenceId;
//}
