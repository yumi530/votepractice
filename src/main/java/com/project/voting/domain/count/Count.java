package com.project.voting.domain.count;

import com.project.voting.domain.vote.Vote;

import com.project.voting.domain.vote.VoteType;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Count {
    @Id
    @GeneratedValue
    private Long countId;
    private boolean isAgreed;
    private boolean hadVoted;
    private Long scores;

    private int totalRank;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vote_vote_id")
//    private Vote vote;

    private String candidateName;

    @Enumerated(EnumType.STRING)

    VoteType voteType;

    @Builder
    public Count(Long countId, boolean isAgreed, Vote vote, boolean hadVoted, Long scores, String candidateName, VoteType voteType) {
        this.countId = countId;
        this.isAgreed = isAgreed;
//        this.vote = vote;
        this.hadVoted = hadVoted;
        this.scores = scores;
        this.candidateName = candidateName;
        this.voteType = voteType;
    }


}
