package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

public class SessionNotFound extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.SESSION_ID_NOT_FOUND;

    public SessionNotFound(String message) {
        super(ERROR.getMessage(message));
    }

}
