package diegosneves.github.assembleiavota.mapper;

import diegosneves.github.assembleiavota.dto.TopicEntityDTO;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.responses.SessionCreatedResponse;

public class SessionCreatedResponseMapper implements MapperStrategy<SessionCreatedResponse, SessionEntity> {

    @Override
    public SessionCreatedResponse mapFrom(SessionEntity origem) {
        return SessionCreatedResponse.builder()
                .sessionId(origem.getSessionId())
                .topicEntityDTO(BuilderMapper.mapTo(TopicEntityDTO.class, origem.getTopic()))
                .isOpen(origem.isOpen())
                .startTime(origem.getStartTime())
                .endTime(origem.getEndTime())
                .build();
    }
}
