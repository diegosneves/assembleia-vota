package diegosneves.github.assembleiavota.services.contract;

import diegosneves.github.assembleiavota.requests.CastVoteRequest;
import diegosneves.github.assembleiavota.responses.CastVoteResponse;

public interface VoteServiceContract {

    CastVoteResponse performVotingAction(CastVoteRequest request);

}
