package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

/**
 * Representa uma exceção lançada quando um ID de tópico inválido é encontrado.
 *
 * @see RuntimeException
 * @author diegoneves
 */
public class InvalidIdException extends RuntimeException {

    public static final ExceptionHandler ERROR = ExceptionHandler.INVALID_ID_FORMAT;

    public InvalidIdException(String message, Throwable e) {
        super(ERROR.getMessage(message), e);
    }

    public InvalidIdException(String message) {
        super(ERROR.getMessage(message));
    }
}
