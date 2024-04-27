package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

/**
 * Exceção personalizada que é lançada quando não é definido um construtor padrão.
 *
 * @author diegoneves
 */
public class ConstructorDefaultUndefinedException extends RuntimeException {

    public static final ExceptionHandler ERROR = ExceptionHandler.CONSTRUCTOR_DEFAULT_UNDEFINED;

    /**
     * Construtor da exceção {@link ConstructorDefaultUndefinedException}.
     *
     * @param message a mensagem de detalhe da exceção.
     * @param e a causa da exceção.
     */
    public ConstructorDefaultUndefinedException(String message, Throwable e) {
        super(ERROR.getMessage(message), e);
    }
}
