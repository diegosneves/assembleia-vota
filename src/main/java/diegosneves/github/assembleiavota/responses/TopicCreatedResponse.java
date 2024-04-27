package diegosneves.github.assembleiavota.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe responsável por encapsular os dados de resposta após a criação de um {@link diegosneves.github.assembleiavota.models.TopicEntity tópico}.
 * A classe contém os seguintes atributos: ID do tópico, título, descrição e duração da sessão de votação.
 *
 * @author diegoneves
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TopicCreatedResponse {

    private String topicId;
    private String title;
    private String description;
    private Integer votingSessionDuration;

}
