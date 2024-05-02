package diegosneves.github.assembleiavota.repositories;

import diegosneves.github.assembleiavota.models.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteEntityRepository extends JpaRepository<VoteEntity, String> {

    List<VoteEntity> findByTopicId(String topicId);

}
