package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

/**
 * Classe de exceção para erros de validação de CPF.
 * <p>
 * Essa exceção é lançada quando ocorrem problemas na validação do CPF do usuário.
 * <p>
 * Herda diretamente de {@link RuntimeException}, portanto, não precisa ser declarada ou pega.
 *
 * @see RuntimeException
 * @author diegoneves
 */
public class ValidationUserCpfException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.CPF_VALIDATION_FAILED;

    public ValidationUserCpfException(String message, Throwable e) {
        super(ERROR.getMessage(message), e);
    }
}
