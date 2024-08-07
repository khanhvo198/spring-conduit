package mystic.conduit.domain.profile.dto;

import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    public String username;
    public String bio;
    public String image;
    public Boolean following;
}
