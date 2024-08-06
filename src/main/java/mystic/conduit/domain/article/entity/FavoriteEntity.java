package mystic.conduit.domain.article.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.shared.entity.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "favorites")
public class FavoriteEntity extends BaseEntity {

    @Id
    @GeneratedValue
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
