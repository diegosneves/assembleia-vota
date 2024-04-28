package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

public class SessionNotFound extends RuntimeException {

    public static final ExceptionHandler ERROR = ExceptionHandler.SESSION_ID_NOT_FOUND;

    public SessionNotFound(String message) {
        super(ERROR.getMessage(message));
    }

}
