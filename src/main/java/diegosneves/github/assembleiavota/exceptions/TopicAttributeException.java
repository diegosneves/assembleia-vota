package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

/**
 * Classe {@link TopicAttributeException} é uma classe para lidar com exceções específicas
 * que surgem ao manipular atributos de tópicos.
 * Esta classe estende {@link RuntimeException}, o que significa que as exceções desses tipos
 * são unchecked/irrecuperáveis.
 * <p>
 * Ela fornece dois construtores para lançar estas exceções.
 * A particularidade desta classe é que cada instância da exceção vem com um tratador de exceção
 * ({@link ExceptionHandler}), que fornece uma mensagem de erro específica.
 *
 * @author diegoneves
 */
public class TopicAttributeException extends RuntimeException {

    public static final ExceptionHandler ERROR = ExceptionHandler.TOPIC_ATTRIBUTE_INVALID;

    /**
     * Construtor que cria uma nova exceção com uma mensagem de erro padrão.
     *
     * @param message a mensagem detalhada para a exceção.
     */
    public TopicAttributeException(String message) {
        super(ERROR.getMessage(message));
    }

    /**
     * Construtor que cria uma nova exceção com uma mensagem de erro e um tratador de exceção específicos.
     *
     * @param handler o tratador de exceção que fornece a mensagem de erro.
     * @param message a mensagem detalhada para a exceção.
     */
    public TopicAttributeException(ExceptionHandler handler, String message) {
        super(handler.getMessage(message));
    }
}
