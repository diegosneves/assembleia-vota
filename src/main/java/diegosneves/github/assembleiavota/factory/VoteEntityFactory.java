package diegosneves.github.assembleiavota.factory;

import diegosneves.github.assembleiavota.enums.VoteOption;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.VoteEntity;
import diegosneves.github.assembleiavota.utils.UuidUtils;

import java.time.LocalDateTime;

public class VoteEntityFactory {

    private VoteEntityFactory() {}

    public static VoteEntity create(String voterCpf, SessionEntity sessionEntity, String topicId, LocalDateTime votedAt, VoteOption vote) {
        return new VoteEntity(UuidUtils.generateUuid(), voterCpf, sessionEntity, topicId, votedAt, vote);
    }

}
