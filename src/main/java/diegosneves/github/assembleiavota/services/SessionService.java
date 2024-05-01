package diegosneves.github.assembleiavota.services;

import diegosneves.github.assembleiavota.exceptions.IllegalSessionArgumentException;
import diegosneves.github.assembleiavota.exceptions.InvalidIdException;
import diegosneves.github.assembleiavota.exceptions.SessionCreateFailureException;
import diegosneves.github.assembleiavota.exceptions.SessionNotFound;
import diegosneves.github.assembleiavota.exceptions.TopicIdNotFoundException;
import diegosneves.github.assembleiavota.exceptions.UuidUtilsException;
import diegosneves.github.assembleiavota.factory.SessionEntityFactory;
import diegosneves.github.assembleiavota.mapper.MapperStrategy;
import diegosneves.github.assembleiavota.mapper.SessionCreatedResponseMapper;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.repositories.SessionEntityRepository;
import diegosneves.github.assembleiavota.requests.StartSessionRequest;
import diegosneves.github.assembleiavota.responses.SessionCreatedResponse;
import diegosneves.github.assembleiavota.services.contract.SessionServiceContract;
import diegosneves.github.assembleiavota.services.contract.TopicServiceContract;
import diegosneves.github.assembleiavota.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class SessionService implements SessionServiceContract {

    public static final String NULL_TOPIC_ID = "ID da Pauta não pode ser nulo";
    public static final String INVALID_TOPIC_ID = "ID da Pauta é inválida";
    public static final String TOPIC_DOES_NOT_EXIST = "O ID da Pauta fornecido não foi encontrado no sistema. Certifique-se de que o ID está correto.";
    public static final String SESSION_ID_CANNOT_BE_NULL = "da Sessão não pode ser nulo e";
    public static final String SESSION_CANNOT_BE_NULL = "A Session não pode ser nula";

    private final SessionEntityRepository repository;
    private final TopicServiceContract topicService;

    @Autowired
    public SessionService(SessionEntityRepository repository, TopicServiceContract topicService) {
        this.repository = repository;
        this.topicService = topicService;
    }

    @Override
    public SessionCreatedResponse startSession(StartSessionRequest request) throws SessionCreateFailureException {
        TopicEntity topic = null;
        try {
            topic = this.topicService.getTopic(request.getTopicId());
        } catch (InvalidIdException e) {
            String message = request.getTopicId() == null ? NULL_TOPIC_ID : INVALID_TOPIC_ID;
            log.error(SessionCreateFailureException.ERROR.getMessage(message), e);
            throw new SessionCreateFailureException(message, e);
        } catch (TopicIdNotFoundException e) {
            log.error(SessionCreateFailureException.ERROR.getMessage(TOPIC_DOES_NOT_EXIST), e);
            throw new SessionCreateFailureException(TOPIC_DOES_NOT_EXIST, e);
        }
        SessionEntity session = SessionEntityFactory.create(topic, LocalDateTime.now());
        this.repository.save(session);
        MapperStrategy<SessionCreatedResponse, SessionEntity> mapper = new SessionCreatedResponseMapper();
        return mapper.mapFrom(session);
    }

    @Override
    public SessionEntity getSession(String sessionId) throws SessionNotFound, InvalidIdException {
        Optional<SessionEntity> optionalSession = this.repository.findBySessionId(validateSessionId(sessionId));
        if (optionalSession.isPresent()) {
            SessionEntity currentSession = optionalSession.get();
            return this.updateSession(currentSession);
        }
        log.error(SessionNotFound.ERROR.getMessage(sessionId));
        throw new SessionNotFound(sessionId);
    }

    /**
     * Valida se a SessionId fornecida é válida. Ela não pode ser nula e deve ser um UUID válido.
     *
     * @param sessionId A identificação da sessão a ser validada.
     * @return A session Id se ela for válida.
     * @throws InvalidIdException se o sessionId for nulo ou se não for um UUID válido.
     */
    private static String validateSessionId(String sessionId) throws InvalidIdException {
        if (sessionId == null) {
            log.error(InvalidIdException.ERROR.getMessage(SESSION_ID_CANNOT_BE_NULL));
            throw new InvalidIdException(SESSION_ID_CANNOT_BE_NULL);
        }
        try {
            UuidUtils.isValidUUID(sessionId.trim());
            return sessionId.trim();
        } catch (UuidUtilsException e) {
            log.error(InvalidIdException.ERROR.getMessage(sessionId), e);
            throw new InvalidIdException(sessionId.trim(), e);
        }
    }

    /**
     * Valida se a entidade da sessão fornecida é não-nula.
     *
     * @param session A entidade da sessão que será validada.
     * @throws IllegalSessionArgumentException Se a entidade da sessão for nula.
     */
    private static void sessionValidate(SessionEntity session) throws IllegalSessionArgumentException {
        if (isNull(session)) {
            throw new IllegalSessionArgumentException(SESSION_CANNOT_BE_NULL);
        }
    }

    @Override
    public SessionEntity updateSession(SessionEntity session) throws IllegalSessionArgumentException {
        sessionValidate(session);
        boolean isOpen = LocalDateTime.now().isBefore(session.getEndTime());
        session.setOpen(isOpen);
        return this.repository.save(session);
    }
}
