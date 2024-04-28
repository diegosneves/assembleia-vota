package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

/**
 * Classe representando uma exceção personalizada para quando um tópico ID não é encontrado.
 * Quando essa exceção é acionada, o manipulador de exceções padrão é definido para {@link ExceptionHandler TOPIC_ID_NOT_FOUND}.
 *
 * @author diegoneves
 */
public class TopicIdNotFoundException extends RuntimeException {

    public static final ExceptionHandler ERROR = ExceptionHandler.TOPIC_ID_NOT_FOUND;

    public TopicIdNotFoundException(String message) {
        super(ERROR.getMessage(message));
    }
}
