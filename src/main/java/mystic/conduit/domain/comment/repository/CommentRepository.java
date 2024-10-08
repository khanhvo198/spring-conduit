package mystic.conduit.domain.comment.repository;

import mystic.conduit.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByArticleSlug(String slug);


}
