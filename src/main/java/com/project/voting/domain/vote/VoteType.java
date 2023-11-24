package com.project.voting.domain.vote;

import lombok.Getter;

@Getter
public enum VoteType {
    PROS_CONS("찬반") {
        @Override
        public int getMinCandidates() {
            return 1;
        }
    },
    CHOICE("선택") {
        @Override
        public int getMinCandidates() {
            return 2;
        }
    },
    SCORE("점수") {
        @Override
        public int getMinCandidates() {
            return 2;
        }
    },

    PREFERENCE("선호도") {
        @Override
        public int getMinCandidates() {
            return 2;
        }
    };

    private final String value;

    VoteType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract int getMinCandidates();
}
