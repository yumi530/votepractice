package com.project.voting.service.count;

import com.project.voting.domain.cand_count.CandCount;
import com.project.voting.domain.cand_count.CandCountRepository;
import com.project.voting.domain.count.Count;
import com.project.voting.domain.count.CountRepository;
import com.project.voting.domain.vote.VoteType;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProsConsCountService implements CountService {

    private final CandCountRepository candCountRepository;
    private final CountRepository countRepository;
    private final CommonCountService commonCountService;

    @Override
    public void votesResultConfirm(Long electionId, Long voteId, VoteType voteType) {

        boolean isValid = commonCountService.isValidCount(electionId, voteId, voteType);
        if (isValid) {

            //다시 확인 하기
            CandCount candCount = candCountRepository.findByResult(true);
            CandCount candidate = candCountRepository.findCandidateIdByVoteId(voteId);

            Count count = commonCountService.createCount(electionId, voteId, candidate.getCandidateId());
            count.setElectedYn(candCount.isResult());
            countRepository.save(count);
        }
    }

    @Override
    public VoteType getVoteType() {
        return VoteType.PROS_CONS;
    }
}
