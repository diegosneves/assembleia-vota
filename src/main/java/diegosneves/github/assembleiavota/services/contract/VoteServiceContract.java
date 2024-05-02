package diegosneves.github.assembleiavota.services.contract;

import diegosneves.github.assembleiavota.requests.CastVoteRequest;
import diegosneves.github.assembleiavota.responses.CastVoteResponse;
import diegosneves.github.assembleiavota.responses.CountVotesResponse;

/**
 * A interface {@link VoteServiceContract} representa o contrato para um serviço de votação.
 *
 * @author diegoneves
 */
public interface VoteServiceContract {

    /**
     * Esse método é utilizado para realizar uma ação de votação. A ação inclui as seguintes etapas:
     * <p>
     * <ol>
     *   <li>Verifica a validade da requisição de voto.</li>
     *   <li>Valida o CPF do usuário.</li>
     *   <li>Busca a sessão atual com base no ID fornecido na requisição.</li>
     *   <li>Mapeia e recupera o assunto da sessão.</li>
     *   <li>Submete o voto com base nos dados da requisição, status do usuário e sessão atual.</li>
     *   <li>Retorna uma resposta de voto com informações relevantes sobre a votação.</li>
     * </ol>
     *
     * @param request A requisição de vote, contendo detalhes como CPF do usuário, ID da sessão e opção de voto escolhida.
     * @return Uma resposta para a ação de votação, contendo detalhes como CPF do usuário, status do usuário, ID da sessão, total de votos na sessão, assunto votado, opção de voto escolhida e se o voto foi submetido.
     */
    CastVoteResponse performVotingAction(CastVoteRequest request);

    /**
     * Realiza a soma dos votos para um determinado tópico.
     *
     * @param topicId O ID do tópico para o qual a soma dos votos será calculada.
     * @return Um objeto {@link CountVotesResponse} contendo o DTO do tópico votado,
     * o número total de votos, a quantidade de votos "SIM" e a quantidade de votos "NÃO".
     */
    CountVotesResponse performVotingSum(String topicId);

}
