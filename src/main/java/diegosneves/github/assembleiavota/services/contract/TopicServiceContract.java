package diegosneves.github.assembleiavota.services.contract;

import diegosneves.github.assembleiavota.exceptions.InvalidIdException;
import diegosneves.github.assembleiavota.exceptions.InvalidTopicStringAttributeException;
import diegosneves.github.assembleiavota.exceptions.TopicIdNotFoundException;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.requests.TopicRequest;
import diegosneves.github.assembleiavota.responses.TopicCreatedResponse;


/**
 * Esta interface define o contrato para um Serviço de Tópicos. Ela fornece um método para criar um novo tópico.
 *
 * @author diegoneves
 */
public interface TopicServiceContract {

    /**
     * Cria um novo {@link diegosneves.github.assembleiavota.models.TopicEntity tópico}.
     * Valida o tópico com base no título, descrição e duração da sessão de votação.
     * Persiste o tópico validado no repositório.
     * Retorna uma resposta contendo detalhes do tópico criado.
     *
     * @param topicRequest O objeto de solicitação de tópico contendo o título, a descrição e a duração da sessão de votação.
     * @return uma instância de {@link TopicCreatedResponse} contendo detalhes do tópico criado.
     * @throws InvalidTopicStringAttributeException em caso de título inválido, descrição ou duração da sessão de votação.
     */
    TopicCreatedResponse createNewTopic(TopicRequest topicRequest) throws InvalidTopicStringAttributeException;

    /**
     * Retorna o tópico com base no {@link java.util.UUID ID} fornecido.
     *
     * @param topicId O identificador do tópico que será obtido.
     * @return O objeto {@link TopicEntity} correspondente ao {@link java.util.UUID ID} do tópico fornecido.
     * @throws InvalidIdException  Se o {@link java.util.UUID ID} do tópico fornecido for inválido.
     * @throws TopicIdNotFoundException Se o tópico com o {@link java.util.UUID ID} fornecido não for encontrado.
     */
    TopicEntity getTopic(String topicId) throws InvalidIdException, TopicIdNotFoundException;

}
