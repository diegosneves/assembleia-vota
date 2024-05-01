package diegosneves.github.assembleiavota.exceptions;

import diegosneves.github.assembleiavota.enums.ExceptionDetails;

public class VoteDuplicateException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.CPF_DUPLICATE_VALIDATION_FAILED;

    public VoteDuplicateException(String message, Throwable e) {
        super(ERROR.getMessage(message), e);
    }
}
