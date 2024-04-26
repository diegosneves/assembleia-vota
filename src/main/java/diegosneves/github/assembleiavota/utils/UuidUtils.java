package diegosneves.github.assembleiavota.utils;

import diegosneves.github.assembleiavota.enums.ExceptionHandler;
import diegosneves.github.assembleiavota.exceptions.UuidUtilsException;

import java.util.UUID;

public class UuidUtils {

    private static final ExceptionHandler HANDLER = ExceptionHandler.INVALID_UUID_FORMAT_MESSAGE;

    private UuidUtils() {
    }

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static void isValidUUID(String uuid) throws UuidUtilsException {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException ignored) {
            throw new UuidUtilsException(HANDLER, uuid);
        }
    }

}
