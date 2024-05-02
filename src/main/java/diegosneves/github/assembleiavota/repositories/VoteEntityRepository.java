package diegosneves.github.assembleiavota.repositories;

import diegosneves.github.assembleiavota.models.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A interface {@link VoteEntityRepository} é um repositório para armazenar e recuperar objetos {@link VoteEntity}.
 * Ele estende a interface {@link JpaRepository}, que fornece métodos para operações básicas de {@code CRUD}.
 * A interface fornece um método personalizado {@code findByTopicId}, que retorna uma {@link List lista} de objetos {@link VoteEntity}
 * que correspondem ao topicId fornecido.
 * <p>
 * Este repositório é usado para interagir com o banco de dados subjacente para operações relacionadas à votação.
 *
 * @see JpaRepository
 * @see VoteEntity
 * @author diegoneves
 */
@Repository
public interface VoteEntityRepository extends JpaRepository<VoteEntity, String> {

    List<VoteEntity> findByTopicId(String topicId);

}
