package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

/**
 * A classe {@link SessionNotFound} representa uma exceção lançada quando um ID de sessão não é encontrado.
 *
 * @see RuntimeException
 * @autor diegoneves
 */
public class SessionNotFound extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.SESSION_ID_NOT_FOUND;

    public SessionNotFound(String message) {
        super(ERROR.getMessage(message));
    }

}
