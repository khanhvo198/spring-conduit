package mystic.conduit.domain.article.specification;

import jakarta.persistence.criteria.JoinType;
import mystic.conduit.domain.article.entity.ArticleEntity;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecification {
    public static Specification<ArticleEntity> hasTag(String tag) {
        return (root, query, criteriaBuilder) -> {
            if (tag == null || tag.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("tagList", JoinType.LEFT).get("name"), tag);
        };
    }

    public static Specification<ArticleEntity> hasAuthor(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("author").get("username"), username);
        };
    }

    public static Specification<ArticleEntity> isFavoritedBy(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("favoritedBy", JoinType.LEFT).get("user").get("username"), username);
        };
    }



}
