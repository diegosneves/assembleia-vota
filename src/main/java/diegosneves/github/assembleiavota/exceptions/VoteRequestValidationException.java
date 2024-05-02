package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

/**
 * Exceção lançada para indicar que a validação de uma solicitação de voto falhou.
 *
 * @see RuntimeException
 * @autor diegoneves
 */
public class VoteRequestValidationException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.INVALID_FIELD;

    public VoteRequestValidationException(String message) {
        super(ERROR.getMessage(message));
    }
}
