package mystic.conduit.domain.tag.entity;

import jakarta.persistence.*;
import lombok.*;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.shared.entity.BaseEntity;

@Table(name = "tags")
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity extends BaseEntity {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article", nullable = false)
    private ArticleEntity article;

}
