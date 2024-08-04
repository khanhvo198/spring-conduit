package mystic.conduit.domain.tag.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mystic.conduit.domain.article.entity.ArticleEntity;

@Table(name = "tags")
@Getter
@Setter
@Entity
public class TagEntity {
    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ArticleEntity article;

}
