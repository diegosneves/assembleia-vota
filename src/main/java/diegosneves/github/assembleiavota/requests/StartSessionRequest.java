package diegosneves.github.assembleiavota.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa uma solicitação de início de sessão.
 * <p>
 * Esta classe é usada para criar um objeto de solicitação para iniciar uma sessão.
 * <p>
 * A classe inclui o seguinte atributo:
 * - {@code topicId}: uma string que representa o identificador exclusivo do tópico associado à sessão.
 * <p>
 * Esta classe fornece getters e setters para acessar e modificar este atributo.
 *
 * @author diegoneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StartSessionRequest {
    private String topicId;
}
