package diegosneves.github.assembleiavota.utils;

import diegosneves.github.assembleiavota.exceptions.UuidUtilsException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Classe de utilidade para lidar com operações relacionadas ao {@link UUID}.
 * Esta classe fornece métodos estáticos para gerar um novo {@link UUID} e para validar um {@link UUID} existente.
 *
 * @author diegoneves
 */
@Slf4j
public class UuidUtils {


    private static final String INVALID_UUID_ERROR = "O UUID [{}] é inválido";

    private UuidUtils() {
    }

    /**
     * Gera um novo {@link UUID}.
     *
     * @return A string representando o {@link UUID} gerado.
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Valida um {@link UUID}.
     *
     * @param uuid A string que representa o {@link UUID} a ser validado.
     * @return Retorna 'true' se o {@link UUID} for válido.
     * @throws UuidUtilsException Se o {@link UUID} fornecido for inválido.
     */
    public static boolean isValidUUID(String uuid) throws UuidUtilsException {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException ignored) {
            log.error(INVALID_UUID_ERROR, uuid);
            throw new UuidUtilsException(uuid);
        }
    }

}
