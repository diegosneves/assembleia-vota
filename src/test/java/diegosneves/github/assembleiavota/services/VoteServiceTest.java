package diegosneves.github.assembleiavota.services;

import diegosneves.github.assembleiavota.adapter.SendUserAdapter;
import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import diegosneves.github.assembleiavota.enums.UserStatus;
import diegosneves.github.assembleiavota.enums.VoteOption;
import diegosneves.github.assembleiavota.exceptions.NoVotesFoundForTopicException;
import diegosneves.github.assembleiavota.exceptions.VoteDuplicateException;
import diegosneves.github.assembleiavota.exceptions.VoteRequestValidationException;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.models.VoteEntity;
import diegosneves.github.assembleiavota.repositories.VoteEntityRepository;
import diegosneves.github.assembleiavota.requests.CastVoteRequest;
import diegosneves.github.assembleiavota.responses.CastVoteResponse;
import diegosneves.github.assembleiavota.responses.CountVotesResponse;
import diegosneves.github.assembleiavota.responses.UserResponse;
import diegosneves.github.assembleiavota.services.contract.SessionServiceContract;
import diegosneves.github.assembleiavota.services.contract.TopicServiceContract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static diegosneves.github.assembleiavota.services.SessionServiceTest.DAY;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.DESCRIPTION;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.HOUR;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.MINUTE;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.MONTH;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.TOPIC_UNIQUE_ID;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.UUID_SESSION_TEST;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.VOTACAO_TITLE;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.VOTING_DURATION;
import static diegosneves.github.assembleiavota.services.SessionServiceTest.YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VoteServiceTest {

    public static final String VOTE_UUID = "429aa9b3-afaa-46c6-9054-485629d35dfe";
    public static final String VOTER_CPF = "30433139846";
    @InjectMocks
    private VoteService service;
    @Mock
    private  VoteEntityRepository repository;
    @Mock
    private  SessionServiceContract sessionService;
    @Mock
    private TopicServiceContract topicService;
    @Mock
    private SendUserAdapter userAdapter;

    @Captor
    private ArgumentCaptor<SessionEntity> sessionCaptor;

    private SessionEntity sessionEntity;
    private TopicEntity topicEntity;
    private VoteEntity voteEntity;
    private LocalDateTime time;
    private CastVoteRequest request;
    private CastVoteResponse expectedResponse;

    @BeforeEach
    void setUp() {
        this.request = new CastVoteRequest(VOTER_CPF, UUID_SESSION_TEST, VoteOption.YES);
        this.time = LocalDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTE);
        this.topicEntity = new TopicEntity(TOPIC_UNIQUE_ID, VOTACAO_TITLE, DESCRIPTION, VOTING_DURATION);
        this.sessionEntity = SessionEntity.builder()
                .sessionId(UUID_SESSION_TEST)
                .topic(this.topicEntity)
                .votes(new ArrayList<>())
                .isOpen(true)
                .startTime(this.time)
                .endTime(this.time.plusMinutes(VOTING_DURATION))
                .build();
        this.voteEntity = new VoteEntity(VOTE_UUID, VOTER_CPF, this.sessionEntity, TOPIC_UNIQUE_ID, this.time, VoteOption.YES);
        this.expectedResponse = CastVoteResponse.builder()
                .voterCpf(VOTER_CPF)
                .status(UserStatus.ABLE_TO_VOTE)
                .sessionId(UUID_SESSION_TEST)
                .totalVoters(this.sessionEntity.getVotes().size())
                .topicVoted(new TopicVotedDTO(TOPIC_UNIQUE_ID, this.topicEntity.getTitle(), this.topicEntity.getDescription(), this.topicEntity.getVotingSessionDuration()))
                .chosenVoteOption(VoteOption.YES)
                .voteTaken(true)
                .build();
    }

    @Test
    void shouldCastVoteWhenUserIsEligibleAndSessionisOpen() {
        when(this.sessionService.getSession(UUID_SESSION_TEST)).thenReturn(this.sessionEntity);
        when(this.userAdapter.sendUser(VOTER_CPF)).thenReturn(new UserResponse(UserStatus.ABLE_TO_VOTE));
        when(this.repository.save(any(VoteEntity.class))).thenReturn(this.voteEntity);

        CastVoteResponse response = this.service.performVotingAction(this.request);

        verify(this.sessionService, times(1)).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, times(1)).updateSession(this.sessionCaptor.capture());
        verify(this.repository, times(1)).save(any(VoteEntity.class));
        verify(this.userAdapter, times(1)).sendUser(anyString());

        assertNotNull(response);
        assertFalse(this.sessionEntity.getVotes().isEmpty());
        assertEquals(1, this.sessionCaptor.getValue().getVotes().size());
        assertEquals(this.expectedResponse.getVoterCpf(), response.getVoterCpf());
        assertEquals(this.expectedResponse.getSessionId(), response.getSessionId());
        assertEquals(this.expectedResponse.getChosenVoteOption(), response.getChosenVoteOption());
        assertEquals(this.expectedResponse.getStatus(), response.getStatus());
        assertEquals(this.expectedResponse.getTopicVoted().getTitle(), response.getTopicVoted().getTitle());
        assertEquals(this.expectedResponse.getTopicVoted().getDescription(), response.getTopicVoted().getDescription());
        assertEquals(this.expectedResponse.getTopicVoted().getVotingSessionDuration(), response.getTopicVoted().getVotingSessionDuration());
        assertEquals(this.expectedResponse.isVoteTaken(), response.isVoteTaken());
    }

    @Test
    void verifyVotingActionBehaviorWhenUserIsIneligibleToVoteInVoteServiceTest() {
        this.expectedResponse.setVoteTaken(false);
        this.expectedResponse.setStatus(UserStatus.UNABLE_TO_VOTE);

        when(this.sessionService.getSession(UUID_SESSION_TEST)).thenReturn(this.sessionEntity);
        when(this.userAdapter.sendUser(VOTER_CPF)).thenReturn(new UserResponse(UserStatus.UNABLE_TO_VOTE));
        when(this.repository.save(any(VoteEntity.class))).thenReturn(this.voteEntity);

        CastVoteResponse response = this.service.performVotingAction(this.request);

        verify(this.sessionService, times(1)).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, times(1)).sendUser(anyString());

        assertNotNull(response);
        assertEquals(this.expectedResponse.getVoterCpf(), response.getVoterCpf());
        assertEquals(this.expectedResponse.getSessionId(), response.getSessionId());
        assertEquals(this.expectedResponse.getChosenVoteOption(), response.getChosenVoteOption());
        assertEquals(this.expectedResponse.getStatus(), response.getStatus());
        assertEquals(this.expectedResponse.getTopicVoted().getTitle(), response.getTopicVoted().getTitle());
        assertEquals(this.expectedResponse.getTopicVoted().getDescription(), response.getTopicVoted().getDescription());
        assertEquals(this.expectedResponse.getTopicVoted().getVotingSessionDuration(), response.getTopicVoted().getVotingSessionDuration());
        assertEquals(this.expectedResponse.isVoteTaken(), response.isVoteTaken());
    }

    @Test
    void shouldNotCastVoteWhenVotingSessionIsClosed() {
        this.expectedResponse.setVoteTaken(false);
        this.sessionEntity.setOpen(false);

        when(this.sessionService.getSession(UUID_SESSION_TEST)).thenReturn(this.sessionEntity);
        when(this.userAdapter.sendUser(VOTER_CPF)).thenReturn(new UserResponse(UserStatus.ABLE_TO_VOTE));
        when(this.repository.save(any(VoteEntity.class))).thenReturn(this.voteEntity);

        CastVoteResponse response = this.service.performVotingAction(this.request);

        verify(this.sessionService, times(1)).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, times(1)).sendUser(anyString());

        assertNotNull(response);
        assertTrue(this.sessionEntity.getVotes().isEmpty());
        assertEquals(this.expectedResponse.getVoterCpf(), response.getVoterCpf());
        assertEquals(this.expectedResponse.getSessionId(), response.getSessionId());
        assertEquals(this.expectedResponse.getChosenVoteOption(), response.getChosenVoteOption());
        assertEquals(this.expectedResponse.getStatus(), response.getStatus());
        assertEquals(this.expectedResponse.getTopicVoted().getTitle(), response.getTopicVoted().getTitle());
        assertEquals(this.expectedResponse.getTopicVoted().getDescription(), response.getTopicVoted().getDescription());
        assertEquals(this.expectedResponse.getTopicVoted().getVotingSessionDuration(), response.getTopicVoted().getVotingSessionDuration());
        assertEquals(this.expectedResponse.isVoteTaken(), response.isVoteTaken());
    }

    @Test
    void shouldThrowVoteDuplicatedExceptionWhenVoteIsAlreadyExist() {
        this.expectedResponse.setVoteTaken(false);

        when(this.sessionService.getSession(UUID_SESSION_TEST)).thenReturn(this.sessionEntity);
        when(this.userAdapter.sendUser(VOTER_CPF)).thenReturn(new UserResponse(UserStatus.ABLE_TO_VOTE));
        when(this.repository.save(any(VoteEntity.class))).thenThrow(DataIntegrityViolationException.class);

        VoteDuplicateException exception = assertThrows(VoteDuplicateException.class, () -> this.service.performVotingAction(this.request));

        verify(this.sessionService, times(1)).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, times(1)).save(any(VoteEntity.class));
        verify(this.userAdapter, times(1)).sendUser(anyString());

        assertNotNull(exception);
        assertTrue(this.sessionEntity.getVotes().isEmpty());
        assertEquals(VoteDuplicateException.ERROR.getMessage(VOTER_CPF), exception.getMessage());
    }

    @Test
    void shouldNotPerformVotingActionWhenUserCpfIsNull() {
        this.request.setUserCpf(null);

        VoteRequestValidationException exception = assertThrows(VoteRequestValidationException.class, () -> this.service.performVotingAction(this.request));

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());

        assertNotNull(exception);
        assertEquals(VoteRequestValidationException.ERROR.getMessage(VoteService.VOTE_USER_CPF_FIELD), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserCpfIsBlank() {
        this.request.setUserCpf("  ");

        VoteRequestValidationException exception = assertThrows(VoteRequestValidationException.class, () -> this.service.performVotingAction(this.request));

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());

        assertNotNull(exception);
        assertEquals(VoteRequestValidationException.ERROR.getMessage(VoteService.VOTE_USER_CPF_FIELD), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserCpfIsEmpty() {
        this.request.setUserCpf("");

        VoteRequestValidationException exception = assertThrows(VoteRequestValidationException.class, () -> this.service.performVotingAction(this.request));

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());

        assertNotNull(exception);
        assertEquals(VoteRequestValidationException.ERROR.getMessage(VoteService.VOTE_USER_CPF_FIELD), exception.getMessage());
    }

    @Test
    void shouldNotPerformVotingActionWhenUserSessionIdIsNull() {
        this.request.setSessionId(null);

        VoteRequestValidationException exception = assertThrows(VoteRequestValidationException.class, () -> this.service.performVotingAction(this.request));

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());

        assertNotNull(exception);
        assertEquals(VoteRequestValidationException.ERROR.getMessage(VoteService.VOTE_SESSION_ID_FIELD), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserSessionIdIsBlank() {
        this.request.setSessionId("  ");

        VoteRequestValidationException exception = assertThrows(VoteRequestValidationException.class, () -> this.service.performVotingAction(this.request));

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());

        assertNotNull(exception);
        assertEquals(VoteRequestValidationException.ERROR.getMessage(VoteService.VOTE_SESSION_ID_FIELD), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserSessionIdIsEmpty() {
        this.request.setSessionId("");

        VoteRequestValidationException exception = assertThrows(VoteRequestValidationException.class, () -> this.service.performVotingAction(this.request));

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());

        assertNotNull(exception);
        assertEquals(VoteRequestValidationException.ERROR.getMessage(VoteService.VOTE_SESSION_ID_FIELD), exception.getMessage());
    }

    @Test
    void shouldNotPerformVotingActionWhenUserVoteChoiceIsNull() {
        this.request.setChosenVoteOption(null);

        VoteRequestValidationException exception = assertThrows(VoteRequestValidationException.class, () -> this.service.performVotingAction(this.request));

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());

        assertNotNull(exception);
        assertEquals(VoteRequestValidationException.ERROR.getMessage(VoteService.VOTE_CHOICE_FIELD), exception.getMessage());
    }

    @Test
    void shouldReturnCorrectVoteCountWhenPerformingVotingSum() {
        CountVotesResponse responseExpect = CountVotesResponse.builder()
                .topicVoted(TopicVotedDTO.builder()
                        .topicId(this.topicEntity.getTopicId())
                        .title(this.topicEntity.getTitle())
                        .description(this.topicEntity.getDescription())
                        .votingSessionDuration(this.topicEntity.getVotingSessionDuration())
                        .build())
                .votesSum(1)
                .totalVotesInFavor(1)
                .totalVotesAgainst(0)
                .build();
        when(this.topicService.getTopic(TOPIC_UNIQUE_ID)).thenReturn(this.topicEntity);
        when(this.repository.findByTopicId(TOPIC_UNIQUE_ID)).thenReturn(List.of(this.voteEntity));

        CountVotesResponse actual = this.service.performVotingSum(TOPIC_UNIQUE_ID);

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());
        verify(this.topicService, times(1)).getTopic(TOPIC_UNIQUE_ID);
        verify(this.repository, times(1)).findByTopicId(TOPIC_UNIQUE_ID);

        assertNotNull(actual);
        assertEquals(responseExpect.getTopicVoted().getTopicId(), actual.getTopicVoted().getTopicId());
        assertEquals(responseExpect.getTopicVoted().getTitle(), actual.getTopicVoted().getTitle());
        assertEquals(responseExpect.getTopicVoted().getDescription(), actual.getTopicVoted().getDescription());
        assertEquals(responseExpect.getTopicVoted().getVotingSessionDuration(), actual.getTopicVoted().getVotingSessionDuration());
        assertEquals(responseExpect.getVotesSum(), actual.getVotesSum());
        assertEquals(responseExpect.getTotalVotesInFavor(), actual.getTotalVotesInFavor());
        assertEquals(responseExpect.getTotalVotesAgainst(), actual.getTotalVotesAgainst());
    }

    @Test
    void shouldThrowNoVotesFoundForTopicExceptionWhenNoVotesExistForTopic() {
        when(this.topicService.getTopic(TOPIC_UNIQUE_ID)).thenReturn(this.topicEntity);
        when(this.repository.findByTopicId(TOPIC_UNIQUE_ID)).thenReturn(new ArrayList<>());

        NoVotesFoundForTopicException exception = assertThrows(NoVotesFoundForTopicException.class, () -> this.service.performVotingSum(TOPIC_UNIQUE_ID));

        verify(this.sessionService, never()).getSession(UUID_SESSION_TEST);
        verify(this.sessionService, never()).updateSession(any(SessionEntity.class));
        verify(this.repository, never()).save(any(VoteEntity.class));
        verify(this.userAdapter, never()).sendUser(anyString());
        verify(this.topicService, times(1)).getTopic(TOPIC_UNIQUE_ID);
        verify(this.repository, times(1)).findByTopicId(TOPIC_UNIQUE_ID);

        assertNotNull(exception);
        assertEquals(NoVotesFoundForTopicException.ERROR.getMessage(TOPIC_UNIQUE_ID), exception.getMessage());
    }

}
