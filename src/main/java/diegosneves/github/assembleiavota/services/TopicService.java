package diegosneves.github.assembleiavota.services;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;
import diegosneves.github.assembleiavota.exceptions.InvalidTopicIdException;
import diegosneves.github.assembleiavota.exceptions.InvalidTopicStringAttributeException;
import diegosneves.github.assembleiavota.exceptions.InvalidTopicIntegerException;
import diegosneves.github.assembleiavota.exceptions.TopicIdNotFoundException;
import diegosneves.github.assembleiavota.exceptions.UuidUtilsException;
import diegosneves.github.assembleiavota.factory.TopicEntityFactory;
import diegosneves.github.assembleiavota.mapper.BuilderMapper;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.repositories.TopicEntityRepository;
import diegosneves.github.assembleiavota.requests.TopicRequest;
import diegosneves.github.assembleiavota.responses.TopicCreatedResponse;
import diegosneves.github.assembleiavota.services.contract.TopicServiceContract;
import diegosneves.github.assembleiavota.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static diegosneves.github.assembleiavota.enums.ExceptionHandler.TOPIC_ID_NOT_FOUND;

@Service
@Slf4j
public class TopicService implements TopicServiceContract {

    private static final String TOPIC_TITLE = "Titulo";
    private static final String TOPIC_DESCRIPTION = "Descrição";
    private static final String VOTING_DURATION = "Duração";
    private static final int DEFAULT_VALUE = 1;

    private final TopicEntityRepository topicEntityRepository;

    @Autowired
    public TopicService(TopicEntityRepository topicEntityRepository) {
        this.topicEntityRepository = topicEntityRepository;
    }

    @Override
    public TopicCreatedResponse createNewTopic(TopicRequest topicRequest) throws InvalidTopicStringAttributeException {
        TopicEntity topicEntity = validateTopic(topicRequest.getTitle(), topicRequest.getDescription(), topicRequest.getVotingSessionDuration());

        this.topicEntityRepository.save(topicEntity);

        return BuilderMapper.mapTo(TopicCreatedResponse.class, topicEntity);
    }

    @Override
    public TopicEntity getTopic(String topicId) throws InvalidTopicIdException, TopicIdNotFoundException {
        Optional<TopicEntity> topicEntityOptional = topicEntityRepository.findByTopicId(validateTopicId(topicId));
        if (topicEntityOptional.isPresent()) {
            return topicEntityOptional.get();
        } else {
            log.warn(TOPIC_ID_NOT_FOUND.getMessage(topicId));
            throw new TopicIdNotFoundException(topicId);
        }
    }

    /**
     * Valida o {@link java.util.UUID ID} do tópico, que deve estar no formato {@link java.util.UUID UUID}.
     *
     * @param topicId o {@link java.util.UUID ID} do tópico a ser validado
     * @return o {@link java.util.UUID ID} do tópico após ser validado e devidamente formatado
     * @throws InvalidTopicIdException se o {@link java.util.UUID ID} do tópico não estiver no formato {@link java.util.UUID UUID}
     */
    private static String validateTopicId(String topicId) throws InvalidTopicIdException {
        try {
            UuidUtils.isValidUUID(topicId.trim());
            return topicId.trim();
        } catch (UuidUtilsException e) {
            log.error(e.getMessage(), e);
            throw new InvalidTopicIdException(topicId.trim(), e);
        }
    }

    /**
     * Este é um método para validar atributos de um tópico. Ele valida o título, a descrição e a duração da sessão de votação de um
     * tópico. Caso esses atributos estejam de acordo com as regras de validação, uma nova entidade de tópico é criada.
     *
     * @param title                 O título do tópico a ser validado.
     * @param description           A descrição do tópico a ser validada.
     * @param votingSessionDuration A duração da sessão de votação a ser validada.
     * @return Retorna uma nova entidade de tópico se a validação for bem-sucedida.
     * @throws InvalidTopicStringAttributeException Se algum dos atributos for inválido, uma exceção será lançada.
     */
    private static TopicEntity validateTopic(String title, String description, Integer votingSessionDuration) throws InvalidTopicStringAttributeException {
        return TopicEntityFactory.create(
                validateTopicField(TOPIC_TITLE, title),
                validateTopicField(TOPIC_DESCRIPTION, description),
                validateTopicField(VOTING_DURATION, votingSessionDuration));
    }

    /**
     * Valida se o campo (field) de um tópico não é nulo, vazio ou em branco.
     *
     * @param field O nome do campo referente ao tópico.
     * @param value O valor a ser validado.
     * @return Retorna o valor se este for válido.
     * @throws InvalidTopicStringAttributeException Se o valor for nulo, vazio ou em branco,
     *                                              lança uma exceção com uma mensagem de erro, indicando qual campo é inválido.
     */
    private static String validateTopicField(String field, String value) throws InvalidTopicStringAttributeException {
        if (value == null || value.isEmpty() || value.isBlank()) {
            log.error(InvalidTopicStringAttributeException.ERROR.getMessage(field));
            throw new InvalidTopicStringAttributeException(field);
        }
        return value.trim();
    }

    /**
     * Valida um campo do tipo Integer na classe Tópico.
     *
     * @param field O nome do campo referente ao tópico.
     * @param value O valor a ser validado.
     * @return o valor validado; se o valor inicial for menor do que o valor padrão, retorna o valor padrão.
     * @throws InvalidTopicStringAttributeException se o valor do campo for nulo.
     */
    private static Integer validateTopicField(String field, Integer value) throws InvalidTopicStringAttributeException {
        if (value == null) {
            log.error(InvalidTopicIntegerException.ERROR.getMessage(field));
            throw new InvalidTopicIntegerException(field);
        }
        return value < DEFAULT_VALUE ? DEFAULT_VALUE : value;
    }
}

