package diegosneves.github.assembleiavota.repositories;

import diegosneves.github.assembleiavota.models.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Esta interface representa o repositório para objetos {@link TopicEntity}.
 * Estende a interface {@link JpaRepository} e especifica o tipo genérico da entidade ({@link TopicEntity})
 * e o tipo da chave primária (String).
 * <p>
 * Fornece métodos para operações de banco de dados relacionadas à classe {@link TopicEntity},
 * como salvar, excluir e localizar entidades por seu topicId.
 * @see Optional
 * @see TopicEntity
 * @author diegoneves
 */
@Repository
public interface TopicEntityRepository extends JpaRepository<TopicEntity, String> {

    Optional<TopicEntity> findByTopicId(String topicId);

}
