package diegosneves.github.assembleiavota.repositories;

import diegosneves.github.assembleiavota.models.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionEntityRepository extends JpaRepository<SessionEntity, String> {

    Optional<SessionEntity> findBySessionId(String sessionId);

}
