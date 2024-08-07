package mystic.conduit.domain.user.dto;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class UpdateDto {
    private String username;
    private String email;
    private String password;
    private String bio;
    private String image;
}
