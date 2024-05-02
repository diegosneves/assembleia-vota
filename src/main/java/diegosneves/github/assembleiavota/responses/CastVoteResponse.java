package diegosneves.github.assembleiavota.responses;

import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import diegosneves.github.assembleiavota.enums.UserStatus;
import diegosneves.github.assembleiavota.enums.VoteOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A classe {@link CastVoteResponse} representa a resposta retornada após a votação.
 * Contém informações sobre CPF do eleitor, situação (se o usuário está apto a votar ou não),
 * ID da sessão, número total de votantes, tema votado, opção de voto escolhida e se a votação foi realizada ou não.
 *
 * @see VoteOption
 * @see UserStatus
 * @see TopicVotedDTO
 * @author diegoneves
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CastVoteResponse {

    private String voterCpf;
    private UserStatus status;
    private String sessionId;
    private Integer totalVoters;
    private TopicVotedDTO topicVoted;
    private VoteOption chosenVoteOption;
    private boolean voteTaken = false;

}
