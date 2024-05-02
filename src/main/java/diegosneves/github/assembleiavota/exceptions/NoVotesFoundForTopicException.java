package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

public class NoVotesFoundForTopicException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.TOPIC_WITH_NO_VOTES;

    public NoVotesFoundForTopicException(String topicId) {
        super(ERROR.getMessage(topicId));
    }

}
