package diegosneves.github.assembleiavota.repositories;

import diegosneves.github.assembleiavota.models.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicEntityRepository extends JpaRepository<TopicEntity, String> {

    Optional<TopicEntity> findByTopicId(String topicId);

}
