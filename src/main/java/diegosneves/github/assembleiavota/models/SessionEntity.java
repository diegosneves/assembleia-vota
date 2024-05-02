package diegosneves.github.assembleiavota.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
* Representa uma entidade de sessão.
 * <p>
 * A classe {@link SessionEntity} é usada para armazenar informações sobre uma sessão.
 * <p>
 * A classe inclui os seguintes atributos:
 * <ul>
 * <li> {@code sessionId:} Uma string que representa o identificador exclusivo da sessão.</li>
 * <li> {@code tópico:} um objeto {@link TopicEntity} que representa o tópico associado à sessão.</li>
 * <li> {@code isOpen:} Um booleano que indica se a sessão está aberta.</li>
 * <li> {@code startTime:} um objeto {@link LocalDateTime} que representa a hora de início da sessão.</li>
 * <li> {@code endTime:} um objeto {@link LocalDateTime} que representa o horário de término da sessão.</li>
 * <li> {@code votos:} uma lista de objetos {@link VoteEntity} que representam os votos associados à sessão.</li>
 * </ul>
 *
 * Esta classe fornece getters e setters para acessar e modificar esses atributos.
 *
 * @author diegoneves
 */
@Entity(name = "sessions")
@Table(name = "sessions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SessionEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String sessionId;
    @ManyToOne
    private TopicEntity topic;
    private boolean isOpen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @OneToMany(mappedBy = "session")
    private List<VoteEntity> votes;

}
