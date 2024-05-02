package diegosneves.github.assembleiavota.repositories;

import diegosneves.github.assembleiavota.models.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A interface SessionEntityRepository fornece métodos para acessar e manipular objetos {@link SessionEntity} no banco de dados.
 * Estende a interface {@link JpaRepository}, que fornece operações {@code CRUD} básicas para a entidade.
 * <p>
 * A interface inclui os seguintes métodos:
 * - {@code findBySessionId}: recupera um objeto {@link Optional Opcional} contendo uma {@link SessionEntity} com o sessionId fornecido.
 *
 * @see JpaRepository
 * @see Optional
 * @see SessionEntity
 * @author diegoneves
 */
@Repository
public interface SessionEntityRepository extends JpaRepository<SessionEntity, String> {

    Optional<SessionEntity> findBySessionId(String sessionId);

}
