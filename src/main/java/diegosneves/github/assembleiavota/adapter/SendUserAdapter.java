package diegosneves.github.assembleiavota.adapter;

import diegosneves.github.assembleiavota.exceptions.ValidationUserCpfException;
import diegosneves.github.assembleiavota.responses.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class SendUserAdapter extends HttpAdapter {

    public static final String CPF_VALIDATION_ERROR = "CPF inválido. Por favor, verifique o CPF informado e tente novamente.";
    private final String url;

    @Autowired
    public SendUserAdapter(@Value("${spring.api.url.validator-fiscal}") String url) {
        this.url = url;
    }

    /**
     * Método responsável por enviar uma solicitação GET e receber a resposta contendo as informações do usuário com o CPF especificado.
     *
     * @param cpf String que representa o Cadastro de Pessoa Física (CPF) do usuário.
     * @return Um objeto {@link UserResponse} contendo as informações do usuário.
     * @throws ValidationUserCpfException Se ocorrer um erro durante as Operações de solicitação/recepção.
     */
    public UserResponse sendUser(String cpf) throws ValidationUserCpfException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url).pathSegment(cpf);
        try {
            ResponseEntity<UserResponse> response = this.restTemplateSimpleWebClient.getRestTemplate()
                    .getForEntity(builder.toUriString(), UserResponse.class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error(ValidationUserCpfException.ERROR.getMessage(e.getLocalizedMessage()), e);
            throw new ValidationUserCpfException(CPF_VALIDATION_ERROR, e);
        }
    }
}
