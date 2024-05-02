package diegosneves.github.assembleiavota.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Esta classe representa um Data Transfer Object (DTO) para um tópico votado.
 * Contém informações sobre o tema que foi votado, como seu ID,
 * título, descrição e duração da sessão de votação.
 *
 * @autor diegoneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TopicVotedDTO {

    private String topicId;
    private String title;
    private String description;
    private Integer votingSessionDuration;
}
