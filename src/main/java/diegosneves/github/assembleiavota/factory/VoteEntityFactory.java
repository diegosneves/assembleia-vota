package diegosneves.github.assembleiavota.factory;

import diegosneves.github.assembleiavota.enums.VoteOption;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.VoteEntity;
import diegosneves.github.assembleiavota.utils.UuidUtils;

import java.time.LocalDateTime;

/**
 * A classe {@link VoteEntityFactory} é uma classe de fábrica para criar instâncias da classe {@link VoteEntity}.
 * Fornece um método estático que pega os parâmetros necessários para criar um objeto {@link VoteEntity} e o retorna.
 *
 * @autor diegoneves
 */
public class VoteEntityFactory {

    private VoteEntityFactory() {}

    public static VoteEntity create(String voterCpf, SessionEntity sessionEntity, String topicId, LocalDateTime votedAt, VoteOption vote) {
        return new VoteEntity(UuidUtils.generateUuid(), voterCpf, sessionEntity, topicId, votedAt, vote);
    }

}
