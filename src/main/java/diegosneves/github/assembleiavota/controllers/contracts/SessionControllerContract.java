package diegosneves.github.assembleiavota.controllers.contracts;

import diegosneves.github.assembleiavota.requests.StartSessionRequest;
import diegosneves.github.assembleiavota.responses.SessionCreatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interface {@link SessionControllerContract} representa o contrato para a gestão de sessões de votação.
 *
 * @author diegoneves
 */
public interface SessionControllerContract {

    /**
     * Inicia uma sessão de votação.
     *
     * @param request o objeto StartSessionRequest contendo o ID da pauta para a qual deseja-se iniciar a sessão de votação.
     * @return um objeto ResponseEntity contendo um objeto {@link SessionCreatedResponse} que contém as informações da sessão criada.
     */
    @PostMapping(value = "/start", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Inicia uma sessão de votação",
            description = "Este endpoint é responsável por iniciar uma sessão de votação, requerendo o id da Pauta(Topic) como input",
            tags = "Sessão"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Sessão de votação criada com sucesso!",
                    content = @Content)
    })
    ResponseEntity<SessionCreatedResponse> startSession(@RequestBody StartSessionRequest request);

}
