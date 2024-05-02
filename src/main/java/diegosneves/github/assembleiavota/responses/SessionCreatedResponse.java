package diegosneves.github.assembleiavota.responses;

import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Esta classe representa uma resposta criada pela sessão.
 * Contém informações sobre a sessão criada, como seu ID,
 * o tópico votado DTO, se está aberto, horário de início e horário de término.
 *
 * @see TopicVotedDTO
 * @author diegoneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SessionCreatedResponse {

    private String sessionId;
    private TopicVotedDTO topicVotedDTO;
    private boolean isOpen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


}
