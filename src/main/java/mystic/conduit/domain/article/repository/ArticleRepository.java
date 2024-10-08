package mystic.conduit.domain.article.repository;

import mystic.conduit.domain.article.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> ,JpaSpecificationExecutor<ArticleEntity>, PagingAndSortingRepository<ArticleEntity, Long> {

    Page<ArticleEntity> findByAuthorIdIn(List<Long> authorIds, Pageable pageable);

    Optional<ArticleEntity> findBySlug(String slug);

    void deleteBySlug(String slug);

    Optional<ArticleEntity> findBySlugAndAuthorId(String slug, Long id);

}
