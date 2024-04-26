package diegosneves.github.assembleiavota.controllers.contracts;

import diegosneves.github.assembleiavota.requests.TopicRequest;
import diegosneves.github.assembleiavota.responses.TopicCreatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * A interface {@link TopicControllerContract} define o contrato para um controlador de tópicos que manipula operações relacionadas aos tópicos.
 * Ela fornece um método para criar um novo tópico.
 *
 * @author diegoneves
 */
public interface TopicControllerContract {

    /**
     * Cria um novo tópico.
     *
     * @param request O corpo da requisição contendo os detalhes do tópico.
     * @return ResponseEntity A entidade de resposta contendo os detalhes do tópico criado.
     */
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Cadastra uma nova pauta",
            description = "Este endpoint é responsável por criar uma nova pauta no banco de dados. Para isso, ele usa a requisição JSON do corpo da chamada HTTP. A pauta é armazenada e uma resposta de sucesso é retornada quando a operação é concluída com êxito.",
            tags = "Pautas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pauta cadastrada com sucesso",
                    content = @Content)
    })
    ResponseEntity<TopicCreatedResponse> createTopic(@RequestBody TopicRequest request);

}
