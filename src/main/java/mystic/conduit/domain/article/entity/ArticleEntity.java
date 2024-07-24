package mystic.conduit.domain.article.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class ArticleEntity {
    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    private String slug;

}
