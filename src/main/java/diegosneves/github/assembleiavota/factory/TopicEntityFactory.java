package diegosneves.github.assembleiavota.factory;

import diegosneves.github.assembleiavota.models.TopicEntity;

import java.util.UUID;

public class TopicEntityFactory {

    private TopicEntityFactory() {}

    public static TopicEntity create(String title, String description, Integer votingSessionDuration) {
        return new TopicEntity(UUID.randomUUID().toString(), title, description, votingSessionDuration);
    }

}
