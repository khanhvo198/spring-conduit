package mystic.conduit.domain.user.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateDto {
    private String username;
    private String email;
    private String password;
    private String bio;
    private String image;
}
