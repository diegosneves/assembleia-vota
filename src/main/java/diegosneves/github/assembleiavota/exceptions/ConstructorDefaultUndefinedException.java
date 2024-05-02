package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

/**
 * Exceção personalizada que é lançada quando não é definido um construtor padrão.
 *
 * @see RuntimeException
 * @author diegoneves
 */
public class ConstructorDefaultUndefinedException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.CONSTRUCTOR_DEFAULT_UNDEFINED;

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
