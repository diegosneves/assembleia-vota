package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

/**
 * Esta classe representa uma exceção personalizada que é lançada quando ocorre um erro ao criar uma sessão.
 *
 * @author diegoneves
 * @see RuntimeException
 */
public class SessionCreateFailureException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.FAILURE_CREATE_SESSION;

    public SessionCreateFailureException(String message, Throwable e) {
        super(ERROR.getMessage(message), e);
    }
}
