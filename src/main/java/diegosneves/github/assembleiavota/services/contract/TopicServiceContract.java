package diegosneves.github.assembleiavota.services.contract;

import diegosneves.github.assembleiavota.exceptions.InvalidTopicStringAttributeException;
import diegosneves.github.assembleiavota.requests.TopicRequest;
import diegosneves.github.assembleiavota.responses.TopicCreatedResponse;


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

}
