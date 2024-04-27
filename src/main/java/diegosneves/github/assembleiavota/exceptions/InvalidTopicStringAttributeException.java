package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

/**
 * Classe {@link InvalidTopicStringAttributeException} é uma classe para lidar com exceções específicas
 * que surgem ao manipular atributos do tipo {@link String} de tópicos.
 * Esta classe estende {@link RuntimeException}, o que significa que as exceções desses tipos
 * são unchecked/irrecuperáveis.
 * <p>
 *
 * @author diegoneves
 */
public class InvalidTopicStringAttributeException extends RuntimeException {

    public static final ExceptionHandler ERROR = ExceptionHandler.TOPIC_ATTRIBUTE_INVALID;

    /**
     * Construtor que cria uma nova exceção com uma mensagem de erro padrão.
     *
     * @param message a mensagem detalhada para a exceção.
     */
    public InvalidTopicStringAttributeException(String message) {
        super(ERROR.getMessage(message));
    }

}
