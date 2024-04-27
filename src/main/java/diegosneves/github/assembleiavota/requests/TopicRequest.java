package diegosneves.github.assembleiavota.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa uma solicitação de tópico.
 * <p>
 * Esta classe serve como um objeto que transporta dados para criar ou atualizar um {@link diegosneves.github.assembleiavota.models.TopicEntity tópico}.
 * Esses dados incluem o título do tópico, sua descrição e a duração da sessão de votação.
 *
 * @author diegoneves
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TopicRequest {

    private String title;
    private String description;
    private Integer votingSessionDuration;

}
