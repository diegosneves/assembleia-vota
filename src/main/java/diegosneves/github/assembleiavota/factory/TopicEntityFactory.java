package diegosneves.github.assembleiavota.factory;

import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.utils.UuidUtils;

public class TopicEntityFactory {

    private TopicEntityFactory() {}

    public static TopicEntity create(String title, String description, Integer votingSessionDuration) {
        return new TopicEntity(UuidUtils.generateUuid(), title, description, votingSessionDuration);
    }

}
