package mystic.conduit.domain.article.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mystic.conduit.domain.tag.entity.TagEntity;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.shared.entity.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "articles")
public class ArticleEntity extends BaseEntity {

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity author;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<TagEntity> tagList;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<FavoriteEntity> favoriteBy;
}
