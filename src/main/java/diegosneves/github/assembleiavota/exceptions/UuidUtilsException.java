package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;

public class UuidUtilsException extends RuntimeException {


    public UuidUtilsException(ExceptionHandler handler, String message) {
        super(handler.getMessage(message));
    }

}
