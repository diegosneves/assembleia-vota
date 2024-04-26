package diegosneves.github.assembleiavota.utils;

import diegosneves.github.assembleiavota.exceptions.UuidUtilsException;

import java.util.UUID;

public class UuidUtils {


    private UuidUtils() {
    }

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static boolean isValidUUID(String uuid) throws UuidUtilsException {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException ignored) {
            throw new UuidUtilsException(uuid);
        }
    }

}
