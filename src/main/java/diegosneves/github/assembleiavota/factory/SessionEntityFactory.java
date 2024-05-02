package diegosneves.github.assembleiavota.factory;

import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.utils.UuidUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A classe {@link SessionEntityFactory} é uma classe de fábrica responsável por criar uma nova instância de {@link SessionEntity}.
 * Ele fornece um método create() estático que recebe um objeto {@link TopicEntity} e um objeto {@link LocalDateTime} como parâmetros e retorna um novo objeto {@link SessionEntity}.
 * O método create() gera um novo {@link java.util.UUID UUID} usando a classe {@link UuidUtils} e cria um novo objeto {@link SessionEntity} com o UUID gerado, o objeto {@link TopicEntity} fornecido,
 * um valor booleano verdadeiro (indicando que a sessão está aberta), o horário de início fornecido, o horário de término calculado adicionando a duração da sessão de votação do tópico ao início
 *  tempo,
 * e uma lista vazia de votos. O método então retorna o objeto {@link SessionEntity} criado.
 * <p>
 * Exemplo de uso:
 * <pre>{@code
 * TopicEntity topicEntity = new TopicEntity("topicId", "Title", "Description", 60);
 * LocalDateTime startTime = LocalDateTime.now();
 * SessionEntity sessionEntity = SessionEntityFactory.create(topicEntity, startTime);
 * }</pre>
 *
 * @autor diegoneves
 */
public class SessionEntityFactory {

    private SessionEntityFactory() {}

    public static SessionEntity create(TopicEntity topicEntity, LocalDateTime startTime) {
        return new SessionEntity(UuidUtils.generateUuid(), topicEntity, true, startTime, startTime.plusMinutes(topicEntity.getVotingSessionDuration()), new ArrayList<>());
    }

}
