package diegosneves.github.assembleiavota.controllers.contracts;

import diegosneves.github.assembleiavota.requests.CastVoteRequest;
import diegosneves.github.assembleiavota.responses.CastVoteResponse;
import diegosneves.github.assembleiavota.responses.CountVotesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface VoteControllerContract {

    /**
     * Método para realizar a ação de votar.
     * <p>
     * Este método recebe uma requisição de voto ({@link CastVoteRequest}) como parâmetro e delega
     * a ação de votação para o serviço de votação ({@link diegosneves.github.assembleiavota.services.VoteService Voteservice}). No fim, retorna uma
     * resposta encapsulada em um {@link ResponseEntity} com status HTTP 200 (OK) caso bem sucedido.
     *
     * @param request requisição de voto a ser processada.
     * @return ResponseEntity com a resposta da operação de voto ({@link CastVoteResponse}).
     */
    @PostMapping(value = "/cast", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Recebe os dados necessários para efetuar o voto",
            tags = "Votos",
            parameters = {
                    @Parameter(
                            name = "vote",
                            description = "O voto aceita apenas duas opções: Sim ou Não.",
                            schema = @Schema(
                                    enumAsRef = true,
                                    defaultValue = "Sim",
                                    allowableValues = {"Sim", "Não"}))
            })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Voto efetuado com sucesso!",
                    content = @Content)
    })
    ResponseEntity<CastVoteResponse> castVote(@RequestBody CastVoteRequest request);


    /**
     * Conta os votos para um tópico específico.
     *
     * @param topicId O ID do tópico para o qual os votos serão contados.
     * @return Uma {@link ResponseEntity} com a resposta da contagem dos votos ({@link CountVotesResponse}).
     */
    @GetMapping(value = "/sum/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Recebe os ID da Pauta e realiza a contagem dos votos",
            tags = "Votos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contagem de votos para o tópico especificado!",
                    content = @Content)
    })
    ResponseEntity<CountVotesResponse> countVotes(@PathVariable(name = "topicId") String topicId);

}
