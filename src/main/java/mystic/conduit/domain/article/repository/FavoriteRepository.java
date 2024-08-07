package mystic.conduit.domain.article.repository;

import mystic.conduit.domain.article.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    Optional<FavoriteEntity> findByArticleIdAndUserId(Long articleId, Long userId);
}
