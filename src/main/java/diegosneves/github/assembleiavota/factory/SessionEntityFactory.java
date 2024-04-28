package diegosneves.github.assembleiavota.factory;

import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.utils.UuidUtils;

import java.time.LocalDateTime;

public class SessionEntityFactory {

    private SessionEntityFactory() {}

    public static SessionEntity create(TopicEntity topicEntity, LocalDateTime startTime) {
        return new SessionEntity(UuidUtils.generateUuid(), topicEntity, true, startTime, startTime.plusMinutes(topicEntity.getVotingSessionDuration()));
    }

}
