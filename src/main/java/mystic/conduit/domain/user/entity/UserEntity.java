package mystic.conduit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import mystic.conduit.shared.entity.BaseEntity;
import org.apache.catalina.User;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

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


    @ManyToMany
    @JoinTable(
            name="follows",
            joinColumns = @JoinColumn(name="follower"),
            inverseJoinColumns = @JoinColumn(name="following")
    )
    private List<UserEntity> follower;


    @ManyToMany
    @JoinTable(
            name="follows",
            joinColumns = @JoinColumn(name="following"),
            inverseJoinColumns = @JoinColumn(name="follower")
    )
    private List<UserEntity> following;



}
