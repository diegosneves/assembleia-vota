package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

/**
 * Classe de exceção personalizada que é lançada quando nenhum voto é encontrado para um tópico específico.
 *
 * @see RuntimeException
 * @autor diegoneves
 */
public class NoVotesFoundForTopicException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.TOPIC_WITH_NO_VOTES;

    public NoVotesFoundForTopicException(String topicId) {
        super(ERROR.getMessage(topicId));
    }

}
