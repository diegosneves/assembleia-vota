package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;


/**
 * Esta classe representa uma exceção personalizada que é lançada quando ocorre um erro de formato inválido de {@link java.util.UUID UUID}.
 * Ela estende a classe {@link RuntimeException}, permitindo a semântica de "exceptions não verificadas" do Java,
 * ou seja, não é necessário o uso de try/catch ou throws para lidar com este tipo de exceção.
 * <p>
 * {@link UuidUtilsException} incorpora uma mensagem de erro de um {@link ExceptionDetails manipulador de exceções}, `ERROR`, que é usado para formatar a mensagem da exceção.
 *
 * @see RuntimeException
 * @author diegoneves
 */
public class UuidUtilsException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.INVALID_UUID_FORMAT_MESSAGE;

    /**
     * Construtor da classe {@link UuidUtilsException}
     *
     * @param message A mensagem detalhada da exceção. Esta mensagem é formatada pelo {@link ExceptionDetails manipulador de exceções} `ERROR`
     */
    public UuidUtilsException(String message) {
        super(ERROR.getMessage(message));
    }

}
