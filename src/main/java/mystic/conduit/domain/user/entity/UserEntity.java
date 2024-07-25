package mystic.conduit.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.domain.article.entity.FavoriteEntity;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String bio;

    @Column
    private String image;

}
