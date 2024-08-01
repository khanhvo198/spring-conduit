package mystic.conduit.domain.profile.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;


@Builder
@Getter
@Setter
@JsonTypeName("profile")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    public String username;
    public String bio;
    public String image;
    public Boolean following;
}
