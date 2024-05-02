package diegosneves.github.assembleiavota.mapper;

import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.responses.SessionCreatedResponse;

/**
 * A classe {@link SessionCreatedResponseMapper} é responsável por mapear objetos {@link SessionEntity} para objetos {@link SessionCreatedResponse}.
 *
 * @autor diegoneves
 */
public class SessionCreatedResponseMapper implements MapperStrategy<SessionCreatedResponse, SessionEntity> {

    @Override
    public SessionCreatedResponse mapFrom(SessionEntity origem) {
        return SessionCreatedResponse.builder()
                .sessionId(origem.getSessionId())
                .topicVotedDTO(BuilderMapper.mapTo(TopicVotedDTO.class, origem.getTopic()))
                .isOpen(origem.isOpen())
                .startTime(origem.getStartTime())
                .endTime(origem.getEndTime())
                .build();
    }
}
