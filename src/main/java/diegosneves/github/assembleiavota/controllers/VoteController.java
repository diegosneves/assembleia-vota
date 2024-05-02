package diegosneves.github.assembleiavota.controllers;

import diegosneves.github.assembleiavota.controllers.contracts.VoteControllerContract;
import diegosneves.github.assembleiavota.requests.CastVoteRequest;
import diegosneves.github.assembleiavota.responses.CastVoteResponse;
import diegosneves.github.assembleiavota.responses.CountVotesResponse;
import diegosneves.github.assembleiavota.services.contract.VoteServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/vote")
public class VoteController implements VoteControllerContract {

    private final VoteServiceContract voteService;


    @Autowired
    public VoteController(VoteServiceContract voteService) {
        this.voteService = voteService;
    }

    @Override
    public ResponseEntity<CastVoteResponse> castVote(CastVoteRequest request) {
        CastVoteResponse response = this.voteService.performVotingAction(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CountVotesResponse> countVotes(String topicId) {
        CountVotesResponse response = this.voteService.performVotingSum(topicId);
        return ResponseEntity.ok(response);
    }
}
