package diegosneves.github.assembleiavota.services;

import diegosneves.github.assembleiavota.adapter.SendUserAdapter;
import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import diegosneves.github.assembleiavota.enums.UserStatus;
import diegosneves.github.assembleiavota.exceptions.VoteDuplicateException;
import diegosneves.github.assembleiavota.exceptions.VoteRequestValidationException;
import diegosneves.github.assembleiavota.factory.VoteEntityFactory;
import diegosneves.github.assembleiavota.mapper.BuilderMapper;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.VoteEntity;
import diegosneves.github.assembleiavota.repositories.VoteEntityRepository;
import diegosneves.github.assembleiavota.requests.CastVoteRequest;
import diegosneves.github.assembleiavota.responses.CastVoteResponse;
import diegosneves.github.assembleiavota.responses.UserResponse;
import diegosneves.github.assembleiavota.services.contract.SessionServiceContract;
import diegosneves.github.assembleiavota.services.contract.VoteServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Service
public class VoteService implements VoteServiceContract {

    public static final String VOTE_CHOICE_FIELD = "chosenVoteOption";
    public static final String VOTE_SESSION_ID_FIELD = "sessionId";
    public static final String VOTE_USER_CPF_FIELD = "userCpf";

    private final VoteEntityRepository repository;
    private final SessionServiceContract sessionService;
    private final SendUserAdapter userAdapter;

    @Autowired
    public VoteService(VoteEntityRepository repository, SessionServiceContract sessionService, SendUserAdapter userAdapter) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.userAdapter = userAdapter;
    }

    @Override
    public CastVoteResponse performVotingAction(CastVoteRequest request) {
        voteRequestValidate(request);
        UserStatus userStatus = validateCpf(request.getUserCpf());
        SessionEntity currentSession = this.sessionService.getSession(request.getSessionId());
        TopicVotedDTO topicVotedDTO = BuilderMapper.mapTo(TopicVotedDTO.class, currentSession.getTopic());
        boolean isSubmit = submitVote(request, currentSession, userStatus);
        return new CastVoteResponse(request.getUserCpf(), userStatus, currentSession.getSessionId(), currentSession.getVotes().size(),topicVotedDTO, request.getChosenVoteOption(), isSubmit);
    }

    private void voteRequestValidate(CastVoteRequest request) throws VoteRequestValidationException {
        if (isNull(request.getUserCpf()) || request.getUserCpf().isEmpty() || request.getUserCpf().isBlank()) {
            throw new VoteRequestValidationException(VOTE_USER_CPF_FIELD);
        }
        if (isNull(request.getSessionId()) || request.getSessionId().isEmpty() || request.getSessionId().isBlank()) {
            throw new VoteRequestValidationException(VOTE_SESSION_ID_FIELD);
        }
        if (isNull(request.getChosenVoteOption())) {
            throw new VoteRequestValidationException(VOTE_CHOICE_FIELD);
        }
    }

    private boolean submitVote(CastVoteRequest request, SessionEntity currentSession, UserStatus userStatus) {
        if(currentSession.isOpen() && UserStatus.ABLE_TO_VOTE.equals(userStatus)) {
            String sessionTopicId = currentSession.getTopic().getTopicId();
            VoteEntity vote = VoteEntityFactory.create(request.getUserCpf(), currentSession, sessionTopicId, LocalDateTime.now(), request.getChosenVoteOption());
            validateVote(vote);
            currentSession.getVotes().add(vote);
            this.sessionService.updateSession(currentSession);
            return true;
        }
        return false;
    }

    private void validateVote(VoteEntity vote) throws VoteDuplicateException {
        try {
            this.repository.save(vote);
        } catch (DataIntegrityViolationException e) {
            throw new VoteDuplicateException(vote.getVoterCpf(), e);
        }
    }

    private UserStatus validateCpf(String cpf) {
        UserResponse userResponse = this.userAdapter.sendUser(cpf);
        return userResponse.getStatus();
    }

}
