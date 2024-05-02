package diegosneves.github.assembleiavota.factory;

import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.utils.UuidUtils;

/**
 * Representa uma classe de fábrica para criar objetos {@link TopicEntity}.
 * Esta classe fornece um método estático para criar um novo objeto TopicEntity com os parâmetros fornecidos.
 *
 * @autor diegoneves
 */
public class TopicEntityFactory {

    private TopicEntityFactory() {}

    public static TopicEntity create(String title, String description, Integer votingSessionDuration) {
        return new TopicEntity(UuidUtils.generateUuid(), title, description, votingSessionDuration);
    }

}
