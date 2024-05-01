package diegosneves.github.assembleiavota.controllers.contracts;

import diegosneves.github.assembleiavota.requests.CastVoteRequest;
import diegosneves.github.assembleiavota.responses.CastVoteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface VoteControllerContract {

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

}
