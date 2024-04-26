package diegosneves.github.assembleiavota.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a entidade de um tópico.
 * <p>
 * A classe {@link TopicEntity} é utilizada para armazenar as informações sobre um tópico.
 * <p>
 * A classe inclui os seguintes atributos:
 *  <ul>
 *   <li><code>topicId</code>: Uma string representando o identificador único do tópico.</li>
 *   <li><code>title</code>: Uma string representando o título do tópico.</li>
 *   <li><code>description</code>: Uma string representando a descrição do tópico.</li>
 *   <li><code>votingSessionDuration</code>: Um inteiro representando a duração da sessão de votação do tópico.</li>
 *  </ul>
 * <p>
 * Esta classe fornece os getters e setters necessários para acessar e modificar
 * esses atributos.
 *
 * @author diegoneves
 */
@Entity(name = "topics")
@Table(name = "topics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TopicEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String topicId;
    private String title;
    private String description;
    private Integer votingSessionDuration;

}
