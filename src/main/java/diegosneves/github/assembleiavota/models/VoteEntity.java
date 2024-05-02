package diegosneves.github.assembleiavota.models;

import diegosneves.github.assembleiavota.enums.VoteOption;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * Representa uma Entidade Votante.
 * <p>
 * A classe {@link VoteEntity} é usada para armazenar informações sobre uma votação.
 * Cada voto está associado a um CPF de eleitor, uma sessão, um ID do tópico, um tempo de votação e uma opção de voto.
 * <p>
 * A classe inclui os seguintes atributos:
 * <ul>
 * <li>{@code voteId}: uma string que representa o identificador exclusivo do voto.</li>
 * <li>{@code voterCpf}: Uma string que representa o CPF do eleitor.</li>
 * <li>{@code session}: um objeto {@link SessionEntity} que representa a sessão associada à votação.</li>
 * <li>{@code topicId}: uma string que representa o ID do tópico associado à votação.</li>
 * <li>{@code voteat}: um objeto {@link LocalDateTime} que representa a hora em que o voto foi feito.</li>
 * <li>{@code vote}: um objeto {@link VoteOption} que representa a opção de voto (SIM ou NÃO).</li>
 * </ul>
 *
 * Esta classe fornece getters e setters para acessar e modificar esses atributos.
 *
 * @see SessionEntity
 * @see VoteOption
 * @author diegoneves
 */
@Entity(name = "votes")
@Table(name = "votes", uniqueConstraints= @UniqueConstraint(columnNames={"voterCpf", "topicId"}))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VoteEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String voteId;
    @Column(name = "voterCpf", nullable = false)
    private String voterCpf;
    @ManyToOne
    private SessionEntity session;
    @Column(name = "topicId", nullable = false)
    private String topicId;
    private LocalDateTime votedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "vote", nullable = false)
    private VoteOption vote;

}
