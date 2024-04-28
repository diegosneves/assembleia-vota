package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

/**
 * Classe IllegalSessionArgumentException.
 * <p>
 * Esta classe estende {@link RuntimeException} e é usada para tratar erros
 * relacionados a argumentos de sessão inválidos em uma aplicação.
 * <p>
 * Ela usa um manipulador de exceções pré-definido (ERROR), que possui uma mensagem de erro
 * predefinida para erros de sessão.
 *
 * @see RuntimeException
 * @author diegoneves
 */
public class IllegalSessionArgumentException extends RuntimeException {

    public static final ExceptionHandler ERROR = ExceptionHandler.SESSION_ERROR_MESSAGE;

    public IllegalSessionArgumentException(String message) {
        super(ERROR.getMessage(message));
    }
}
