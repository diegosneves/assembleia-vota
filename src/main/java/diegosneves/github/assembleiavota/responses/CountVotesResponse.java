package diegosneves.github.assembleiavota.responses;

import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A classe {@link CountVotesResponse} é utilizada para encapsular as informações
 * relacionadas à contagem de votos para um determinado tópico.
 * É empregada principalmente para transportar os dados do serviço de votação
 * para o cliente que solicitou a contagem de votos.
 *
 * @author diegoneves
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CountVotesResponse {

    private TopicVotedDTO topicVoted;
    private int votesSum;
    private int totalVotesInFavor;
    private int totalVotesAgainst;

}
