package diegosneves.github.assembleiavota.responses;

import diegosneves.github.assembleiavota.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que representa a resposta retornada na interação com o usuário.
 * O atributo {@link UserStatus 'status'} é usado para determinar se o usuário pode votar ou não.
 *
 * @author diegoneves
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private UserStatus status;
}
