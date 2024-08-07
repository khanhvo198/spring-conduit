package mystic.conduit.domain.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.shared.entity.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
@Builder
public class CommentEntity extends BaseEntity {

    @GeneratedValue
    @Id
    protected Long id;

    @Column
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity author;

}
