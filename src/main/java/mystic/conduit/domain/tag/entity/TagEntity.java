package mystic.conduit.domain.tag.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.shared.entity.BaseEntity;

@Table(name = "tags")
@Getter
@Setter
@Entity
@Builder
public class TagEntity extends BaseEntity {
    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ArticleEntity article;

}
