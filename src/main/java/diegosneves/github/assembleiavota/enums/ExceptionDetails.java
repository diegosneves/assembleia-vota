package diegosneves.github.assembleiavota.enums;

import org.springframework.http.HttpStatus;

/**
 * A classe {@link ExceptionDetails} é uma enumeração que define várias mensagens de exceções.
 * Cada mensagem corresponde a uma condição específica de validação ou erro
 * que pode ocorrer durante as operações.
 *
 * @author diegoneves
 */
public enum ExceptionDetails {

    CONSTRUCTOR_DEFAULT_UNDEFINED("Classe [ %s ] deve declarar um construtor padrão.", HttpStatus.NOT_IMPLEMENTED),
    CLASS_MAPPING_FAILURE("Falha ao tentar mapear a classe [ %s ].", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FIELD("O campo %s não pode ser nulo ou vazio", HttpStatus.BAD_REQUEST),
    TOPIC_NON_NULL_INTEGER_ATTRIBUTE("A %s não pode ser nula", HttpStatus.BAD_REQUEST),
    TOPIC_WITH_NO_VOTES("Nenhum voto foi registrado para o tópico com ID: %s", HttpStatus.NOT_FOUND),
    TOPIC_ID_NOT_FOUND("O ID %s do tópico não foi encontrado", HttpStatus.NOT_FOUND),
    SESSION_ID_NOT_FOUND("O ID %s da Sessão não foi encontrado", HttpStatus.NOT_FOUND),
    CPF_VALIDATION_FAILED("A falha na validação do CPF ocorreu devido ao seguinte motivo: %s", HttpStatus.NOT_FOUND),
    CPF_DUPLICATE_VALIDATION_FAILED("Um voto já foi registrado para este CPF %s nesta pauta", HttpStatus.BAD_REQUEST),
    SESSION_ERROR_MESSAGE("Ocorreu um erro na Session devido ao seguinte motivo: %s", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ID_FORMAT("O ID %s precisa estar no formato UUID (Identificador Único Universal)", HttpStatus.BAD_REQUEST),
    FAILURE_CREATE_SESSION("O sistema falhou ao tentar estabelecer uma nova sessão de votação devido ao seguinte motivo: %s", HttpStatus.BAD_REQUEST),
    INVALID_UUID_FORMAT_MESSAGE("O ID %s precisa estar no formato UUID", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionDetails(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }


    /**
     * Formata uma mensagem com a entrada fornecida e retorna a mensagem formatada.
     *
     * @param message A mensagem de entrada que será formatada.
     * @return A mensagem após a formatação.
     */
    public String getMessage(String message) {
        return String.format(this.message, message);
    }

    /**
     * Retorna o código de status HTTP associado ao erro.
     *
     * @return O código numérico do status HTTP relacionado com o erro.
     */
    public int getStatusCodeValue() {
        return this.httpStatus.value();
    }

    /**
     * Obtém o status HTTP associado ao erro.
     *
     * @return O código de status HTTP relacionado ao erro.
     */
    public HttpStatus getHttpStatusCode() {
        return this.httpStatus;
    }

}
