package diegosneves.github.assembleiavota.services;

import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import diegosneves.github.assembleiavota.exceptions.IllegalSessionArgumentException;
import diegosneves.github.assembleiavota.exceptions.InvalidIdException;
import diegosneves.github.assembleiavota.exceptions.SessionCreateFailureException;
import diegosneves.github.assembleiavota.exceptions.SessionNotFound;
import diegosneves.github.assembleiavota.exceptions.TopicIdNotFoundException;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.repositories.SessionEntityRepository;
import diegosneves.github.assembleiavota.requests.StartSessionRequest;
import diegosneves.github.assembleiavota.responses.SessionCreatedResponse;
import diegosneves.github.assembleiavota.services.contract.TopicServiceContract;
import diegosneves.github.assembleiavota.utils.UuidUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SessionServiceTest {

    public static final String TOPIC_UNIQUE_ID = "4658a51c-3840-453e-bc69-e2b4cff191a4";
    public static final String UUID_SESSION_TEST = "1041148d-3cee-4d97-bafe-5aee1697eb78";
    public static final String VOTACAO_TITLE = "Votacao";
    public static final String DESCRIPTION = "Motivo";
    public static final int VOTING_DURATION = 5;
    public static final int YEAR = 2024;
    public static final int MONTH = 4;
    public static final int DAY = 28;
    public static final int HOUR = 12;
    public static final int MINUTE = 40;

    @InjectMocks
    private SessionService service;
    @Mock
    private SessionEntityRepository repository;
    @Mock
    private TopicServiceContract topicService;

    @Captor
    private ArgumentCaptor<SessionEntity> sessionCaptor;

    private TopicEntity topic;
    private TopicVotedDTO topicDTO;
    private LocalDateTime startDateTime;


    @BeforeEach
    void setUp() {
        this.topic = new TopicEntity(TOPIC_UNIQUE_ID, VOTACAO_TITLE, DESCRIPTION, VOTING_DURATION);
        this.topicDTO = TopicVotedDTO.builder()
                .topicId(TOPIC_UNIQUE_ID)
                .title(VOTACAO_TITLE)
                .description(DESCRIPTION)
                .votingSessionDuration(VOTING_DURATION)
                .build();
        this.startDateTime = LocalDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTE);
    }

    @Test
    void shouldCreateSessionAndValidateAttributes() {
        SessionCreatedResponse expect = SessionCreatedResponse.builder()
                .sessionId(UUID_SESSION_TEST)
                .topicVotedDTO(this.topicDTO)
                .isOpen(true)
                .startTime(this.startDateTime)
                .endTime(this.startDateTime.plusMinutes(VOTING_DURATION))
                .build();
        SessionEntity sessionEntity = SessionEntity.builder()
                .sessionId(UUID_SESSION_TEST)
                .topic(this.topic)
                .isOpen(true)
                .startTime(this.startDateTime)
                .endTime(this.startDateTime.plusMinutes(VOTING_DURATION))
                .build();
        StartSessionRequest request = new StartSessionRequest(TOPIC_UNIQUE_ID);

        when(this.topicService.getTopic(TOPIC_UNIQUE_ID)).thenReturn(this.topic);
        when(this.repository.save(any(SessionEntity.class))).thenReturn(sessionEntity);

        SessionCreatedResponse actual = this.service.startSession(request);

        verify(this.topicService, times(1)).getTopic(TOPIC_UNIQUE_ID);
        verify(this.repository, times(1)).save(this.sessionCaptor.capture());

        assertNotNull(actual);
        SessionEntity captorValue = this.sessionCaptor.getValue();
        assertTrue(UuidUtils.isValidUUID(actual.getSessionId()));
        assertEquals(expect.getTopicVotedDTO().getTopicId(), actual.getTopicVotedDTO().getTopicId());
        assertEquals(expect.isOpen(), actual.isOpen());
        assertNotNull(actual.getStartTime());
        assertNotNull(actual.getEndTime());
        assertEquals(actual.getStartTime().plusMinutes(VOTING_DURATION), actual.getEndTime());
        assertTrue(UuidUtils.isValidUUID(captorValue.getSessionId()));
        assertEquals(this.topic, captorValue.getTopic());
        assertEquals(captorValue.getStartTime().plusMinutes(VOTING_DURATION), captorValue.getEndTime());
    }

    @Test
    void shouldThrowExceptionWhenStartingSessionWithNullTopicId() {
        StartSessionRequest request = new StartSessionRequest(null);

        when(this.topicService.getTopic(null)).thenThrow(new InvalidIdException(TopicService.TOPIC_ID_NULL_ERROR));

        SessionCreateFailureException actual = assertThrows(SessionCreateFailureException.class, () -> this.service.startSession(request));

        assertNotNull(actual);
        assertEquals(SessionCreateFailureException.ERROR.getMessage(SessionService.NULL_TOPIC_ID), actual.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenStartingSessionWithInvalidTopicId() {
        String invalidTopicId = "234234243-sfdgsdf";
        StartSessionRequest request = new StartSessionRequest(invalidTopicId);

        when(this.topicService.getTopic(invalidTopicId)).thenThrow(new InvalidIdException(invalidTopicId));

        SessionCreateFailureException actual = assertThrows(SessionCreateFailureException.class, () -> this.service.startSession(request));

        assertNotNull(actual);
        assertEquals(SessionCreateFailureException.ERROR.getMessage(SessionService.INVALID_TOPIC_ID), actual.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenStartingSessionWithTopicIdNotExist() {
        StartSessionRequest request = new StartSessionRequest(TOPIC_UNIQUE_ID);

        when(this.topicService.getTopic(TOPIC_UNIQUE_ID)).thenThrow(new TopicIdNotFoundException(TOPIC_UNIQUE_ID));

        SessionCreateFailureException actual = assertThrows(SessionCreateFailureException.class, () -> this.service.startSession(request));

        assertNotNull(actual);
        assertEquals(SessionCreateFailureException.ERROR.getMessage(SessionService.TOPIC_DOES_NOT_EXIST), actual.getMessage());

    }

    @Test
    void shouldReturnSessionEntityWhenValidSessionIdIsGiven() {
        SessionEntity sessionEntity = SessionEntity.builder()
                .sessionId(UUID_SESSION_TEST)
                .topic(this.topic)
                .isOpen(true)
                .startTime(this.startDateTime)
                .endTime(this.startDateTime.plusMinutes(VOTING_DURATION))
                .build();
        when(this.repository.findBySessionId(UUID_SESSION_TEST)).thenReturn(Optional.ofNullable(sessionEntity));
        when(this.repository.save(any(SessionEntity.class))).thenReturn(sessionEntity);

        SessionEntity actual = this.service.getSession(UUID_SESSION_TEST);

        verify(this.repository, times(1)).findBySessionId(UUID_SESSION_TEST);

        assertNotNull(actual);
        assertEquals(sessionEntity, actual);
    }

    @Test
    void shouldThrowSessionNotFoundWhenInvalidSessionIdIsGiven() {
        when(this.repository.findBySessionId(UUID_SESSION_TEST)).thenReturn(Optional.empty());

        SessionNotFound actual = assertThrows(SessionNotFound.class, () -> this.service.getSession(UUID_SESSION_TEST));

        verify(this.repository, times(1)).findBySessionId(UUID_SESSION_TEST);

        assertNotNull(actual);
        assertEquals(SessionNotFound.ERROR.getMessage(UUID_SESSION_TEST), actual.getMessage());
    }

    @Test
    void shouldThrowInvalidIdExceptionWhenNullSessionIdIsGiven() {

        InvalidIdException actual = assertThrows(InvalidIdException.class, () -> this.service.getSession(null));

        verify(this.repository, never()).findBySessionId(null);

        assertNotNull(actual);
        assertEquals(InvalidIdException.ERROR.getMessage(SessionService.SESSION_ID_CANNOT_BE_NULL), actual.getMessage());
    }

    @Test
    void shouldThrowInvalidIdExceptionWhenInvalidSessionIdIsGiven() {
        String sessionId = "id-invalid-123";

        InvalidIdException actual = assertThrows(InvalidIdException.class, () -> this.service.getSession(sessionId));

        verify(this.repository, never()).findBySessionId(sessionId);

        assertNotNull(actual);
        assertEquals(InvalidIdException.ERROR.getMessage(sessionId), actual.getMessage());
    }


    @Test
    void shouldThrowIllegalSessionArgumentExceptionWhenSessionIsNull() {
        IllegalSessionArgumentException actual = assertThrows(IllegalSessionArgumentException.class, () -> this.service.updateSession(null));

        verify(this.repository, never()).save(any(SessionEntity.class));

        assertNotNull(actual);
        assertEquals(IllegalSessionArgumentException.ERROR.getMessage(SessionService.SESSION_CANNOT_BE_NULL), actual.getMessage());
    }

    @Test
    void shouldNotSaveNullSessionAndThrowIllegalSessionArgumentException() {
        IllegalSessionArgumentException actual = assertThrows(IllegalSessionArgumentException.class, () -> this.service.updateSession(null));

        verify(this.repository, never()).save(any(SessionEntity.class));

        assertNotNull(actual);
        assertEquals(IllegalSessionArgumentException.ERROR.getMessage(SessionService.SESSION_CANNOT_BE_NULL), actual.getMessage());
    }

    @Test
    void shouldUpdateSessionSuccessfully() {
        SessionEntity sessionEntity = SessionEntity.builder()
                .sessionId(UUID_SESSION_TEST)
                .topic(this.topic)
                .isOpen(true)
                .startTime(this.startDateTime)
                .endTime(this.startDateTime.plusMinutes(VOTING_DURATION))
                .build();

        when(this.repository.save(sessionEntity)).thenReturn(sessionEntity);

        SessionEntity entity = this.service.updateSession(sessionEntity);

        verify(this.repository, times(1)).save(sessionEntity);

        assertNotNull(entity);
        assertEquals(sessionEntity, entity);
    }

}
