package diegosneves.github.assembleiavota.enums;

import org.springframework.http.HttpStatus;

/**
 * A classe {@link ExceptionHandler} é uma enumeração que define várias mensagens de exceções.
 * Cada mensagem corresponde a uma condição específica de validação ou erro
 * que pode ocorrer durante as operações.
 *
 * @author diegoneves
 */
public enum ExceptionHandler {

    CONSTRUCTOR_DEFAULT_UNDEFINED("Classe [ %s ] deve declarar um construtor padrão.", HttpStatus.NOT_IMPLEMENTED),
    CLASS_MAPPING_FAILURE("Falha ao tentar mapear a classe [ %s ].", HttpStatus.INTERNAL_SERVER_ERROR),
    TOPIC_ATTRIBUTE_INVALID("O [%s] não pode ser nulo ou vazio", HttpStatus.BAD_REQUEST),
    TOPIC_NON_NULL_INTEGER_ATTRIBUTE("A [%s] não pode ser nula", HttpStatus.BAD_REQUEST),
    INVALID_UUID_FORMAT_MESSAGE("O ID [%s] precisa ser no formato UUID", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionHandler(String message, HttpStatus httpStatus) {
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
