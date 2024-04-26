package diegosneves.github.assembleiavota.enums;

/**
 * A classe {@link ExceptionHandler} é uma enumeração que define várias mensagens de exceções.
 * Cada mensagem corresponde a uma condição específica de validação ou erro
 * que pode ocorrer durante as operações.
 *
 * @author diegoneves
 */
public enum ExceptionHandler {

    INVALID_UUID_FORMAT_MESSAGE("O ID [%s] precisa ser no formato UUID");

    private final String message;

    ExceptionHandler(String message) {
        this.message = message;

    }

    /**
     * Retorna a mensagem associada à exceção.
     *
     * @return A mensagem associada à exceção.
     */
    public String getMessage() {
        return this.message;
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

}
