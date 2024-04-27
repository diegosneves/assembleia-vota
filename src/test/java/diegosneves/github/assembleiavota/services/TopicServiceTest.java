package diegosneves.github.assembleiavota.services;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;
import diegosneves.github.assembleiavota.exceptions.TopicAttributeException;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.repositories.TopicEntityRepository;
import diegosneves.github.assembleiavota.requests.TopicRequest;
import diegosneves.github.assembleiavota.responses.TopicCreatedResponse;
import diegosneves.github.assembleiavota.utils.UuidUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TopicServiceTest {

    private static final String UUID_TEST = "4658a51c-3840-453e-bc69-e2b4cff191a4";
    private static final String TOPIC_TITLE = "Titulo";
    private static final String TOPIC_DESCRIPTION = "Descrição";
    private static final String VOTING_DURATION = "Duração";
    private static final int DEFAULT_VALUE = 1;

    @InjectMocks
    private TopicService topicService;

    @Mock
    private TopicEntityRepository topicEntityRepository;

    @Captor
    private ArgumentCaptor<TopicEntity> topicEntitytCaptor;


    private TopicEntity topicEntity;

    @BeforeEach
    void setUp() {

        this.topicEntity = new TopicEntity(UUID_TEST, "Votacao", "Motivo", 2);

    }

    @Test
    void shouldCreateTopicAndVerifyWithRequestedDetails() {
        TopicRequest topicRequest = TopicRequest.builder()
                .title("Votacao")
                .description("Motivo")
                .votingSessionDuration(2)
                .build();
        when(this.topicEntityRepository.save(any(TopicEntity.class))).thenReturn(this.topicEntity);

        TopicCreatedResponse actualResponse = this.topicService.createNewTopic(topicRequest);

        verify(this.topicEntityRepository, times(1)).save(this.topicEntitytCaptor.capture());

        TopicEntity captorValue = this.topicEntitytCaptor.getValue();
        assertNotNull(actualResponse);
        assertNotNull(captorValue);
        assertTrue(UuidUtils.isValidUUID(captorValue.getTopicId()));
        assertEquals(topicRequest.getTitle().trim(), captorValue.getTitle());
        assertEquals(topicRequest.getDescription().trim(), captorValue.getDescription());
        assertEquals(topicRequest.getVotingSessionDuration(), captorValue.getVotingSessionDuration());
        assertTrue(UuidUtils.isValidUUID(actualResponse.getTopicId()));
        assertEquals("Votacao", actualResponse.getTitle());
        assertEquals("Motivo", actualResponse.getDescription());
        assertEquals(2, actualResponse.getVotingSessionDuration());
    }

    @Test
    void shouldCreateAndValidateTopicWithDefaultVotingSessionDuration() {
        TopicRequest topicRequest = TopicRequest.builder()
                .title("Votacao ")
                .description("Motivo")
                .votingSessionDuration(0)
                .build();
        when(this.topicEntityRepository.save(any(TopicEntity.class))).thenReturn(this.topicEntity);

        TopicCreatedResponse actualResponse = this.topicService.createNewTopic(topicRequest);

        verify(this.topicEntityRepository, times(1)).save(this.topicEntitytCaptor.capture());

        TopicEntity captorValue = this.topicEntitytCaptor.getValue();
        assertNotNull(actualResponse);
        assertNotNull(captorValue);
        assertTrue(UuidUtils.isValidUUID(captorValue.getTopicId()));
        assertEquals(topicRequest.getTitle().trim(), captorValue.getTitle());
        assertEquals(topicRequest.getDescription().trim(), captorValue.getDescription());
        assertNotEquals(topicRequest.getVotingSessionDuration(), captorValue.getVotingSessionDuration());
        assertEquals(1, captorValue.getVotingSessionDuration());
        assertTrue(UuidUtils.isValidUUID(actualResponse.getTopicId()));
        assertEquals("Votacao", actualResponse.getTitle());
        assertEquals("Motivo", actualResponse.getDescription());
        assertEquals(1, actualResponse.getVotingSessionDuration());
    }

    @Test
    void shouldThrowExceptionWhenCreatingTopicWithStringFieldEmpty() {
        TopicRequest topicRequest = TopicRequest.builder()
                .title("")
                .description("Motivo")
                .votingSessionDuration(2)
                .build();
        when(this.topicEntityRepository.save(any(TopicEntity.class))).thenReturn(this.topicEntity);

        TopicAttributeException actual = assertThrows(TopicAttributeException.class, () -> this.topicService.createNewTopic(topicRequest));

        verify(this.topicEntityRepository, never()).save(any(TopicEntity.class));

        assertNotNull(actual);
        assertEquals(ExceptionHandler.TOPIC_ATTRIBUTE_INVALID.getMessage(TOPIC_TITLE), actual.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreatingTopicWithStringFieldNull() {
        TopicRequest topicRequest = TopicRequest.builder()
                .title(null)
                .description("Motivo")
                .votingSessionDuration(2)
                .build();
        when(this.topicEntityRepository.save(any(TopicEntity.class))).thenReturn(this.topicEntity);

        TopicAttributeException actual = assertThrows(TopicAttributeException.class, () -> this.topicService.createNewTopic(topicRequest));

        verify(this.topicEntityRepository, never()).save(any(TopicEntity.class));

        assertNotNull(actual);
        assertEquals(ExceptionHandler.TOPIC_ATTRIBUTE_INVALID.getMessage(TOPIC_TITLE), actual.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreatingTopicWithStringFieldBlank() {
        TopicRequest topicRequest = TopicRequest.builder()
                .title("  ")
                .description("Motivo")
                .votingSessionDuration(2)
                .build();
        when(this.topicEntityRepository.save(any(TopicEntity.class))).thenReturn(this.topicEntity);

        TopicAttributeException actual = assertThrows(TopicAttributeException.class, () -> this.topicService.createNewTopic(topicRequest));

        verify(this.topicEntityRepository, never()).save(any(TopicEntity.class));

        assertNotNull(actual);
        assertEquals(ExceptionHandler.TOPIC_ATTRIBUTE_INVALID.getMessage(TOPIC_TITLE), actual.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCreatingTopicWithIntegerFieldNull() {
        TopicRequest topicRequest = TopicRequest.builder()
                .title("Titulo")
                .description("Motivo")
                .votingSessionDuration(null)
                .build();
        when(this.topicEntityRepository.save(any(TopicEntity.class))).thenReturn(this.topicEntity);

        TopicAttributeException actual = assertThrows(TopicAttributeException.class, () -> this.topicService.createNewTopic(topicRequest));

        verify(this.topicEntityRepository, never()).save(any(TopicEntity.class));

        assertNotNull(actual);
        assertEquals(ExceptionHandler.TOPIC_NON_NULL_INTEGER_ATTRIBUTE.getMessage(VOTING_DURATION), actual.getMessage());
    }

}
