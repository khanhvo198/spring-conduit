package mystic.conduit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
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


    @ManyToMany
    @JoinTable(
            name="follows",
            joinColumns = @JoinColumn(name="follower_id"),
            inverseJoinColumns = @JoinColumn(name="following_id")
    )
    private Set<UserEntity> followers;


    @ManyToMany
    @JoinTable(
            name="follows",
            joinColumns = @JoinColumn(name="following_id"),
            inverseJoinColumns = @JoinColumn(name="follower_id")
    )
    private Set<UserEntity> followings;



}
