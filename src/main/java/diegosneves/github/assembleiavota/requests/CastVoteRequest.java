package diegosneves.github.assembleiavota.requests;

import diegosneves.github.assembleiavota.enums.VoteOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A classe {@link CastVoteRequest} representa uma solicitação para votar.
 * Contém as informações necessárias para votar, incluindo o CPF do usuário,
 * o ID da sessão na qual o voto deverá ser realizado e a opção de voto escolhida.
 *
 * @see VoteOption
 * @autor diegoneves
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CastVoteRequest {

    private String userCpf;
    private String sessionId;
    private VoteOption chosenVoteOption;

}
