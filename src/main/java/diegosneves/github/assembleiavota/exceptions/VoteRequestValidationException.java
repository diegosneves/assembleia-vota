package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

public class VoteRequestValidationException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.INVALID_FIELD;

    public VoteRequestValidationException(String message) {
        super(ERROR.getMessage(message));
    }
}
