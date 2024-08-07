package mystic.conduit.domain.article.entity;


import jakarta.persistence.*;
import lombok.*;
import mystic.conduit.domain.comment.entity.CommentEntity;
import mystic.conduit.domain.tag.entity.TagEntity;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.shared.entity.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "articles")
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false, unique = true)
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

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagEntity> tagList;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteEntity> favoritedBy;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;

}
