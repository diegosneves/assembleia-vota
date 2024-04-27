package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

/**
 * Classe {@link InvalidTopicIntegerException} é uma classe para lidar com exceções específicas
 * que surgem ao manipular o atributos do tipo {@link Integer} de tópicos.
 * Esta classe estende {@link RuntimeException}, o que significa que as exceções desses tipos
 * são unchecked/irrecuperáveis.
 * <p>
 *
 * @author diegoneves
 */
public class InvalidTopicIntegerException extends RuntimeException {

    public static final ExceptionHandler ERROR = ExceptionHandler.TOPIC_NON_NULL_INTEGER_ATTRIBUTE;

    /**
     * Construtor que cria uma nova exceção com uma mensagem de erro padrão.
     *
     * @param message a mensagem detalhada para a exceção.
     */
    public InvalidTopicIntegerException(String message) {
        super(ERROR.getMessage(message));
    }

}
