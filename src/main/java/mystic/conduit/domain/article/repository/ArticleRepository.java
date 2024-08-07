package mystic.conduit.domain.article.repository;

import mystic.conduit.domain.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> ,JpaSpecificationExecutor<ArticleEntity> {

    List<ArticleEntity> findByAuthorIdIn(List<Long> authorIds);

    Optional<ArticleEntity> findBySlug(String slug);

    void deleteBySlug(String slug);

    Optional<ArticleEntity> findBySlugAndAuthorId(String slug, Long id);

}
