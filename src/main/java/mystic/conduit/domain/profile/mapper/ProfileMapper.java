package mystic.conduit.domain.profile.mapper;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.profile.dto.ProfileDto;
import mystic.conduit.domain.profile.dto.SingleProfileDto;
import mystic.conduit.domain.user.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProfileMapper {
    private final ModelMapper mapper;
    public SingleProfileDto mapToProfileDto(UserEntity user, Boolean following) {
        ProfileDto profile = mapper.map(user, ProfileDto.class);
        profile.setFollowing(following);
        return SingleProfileDto.builder().profile(profile).build();
    }
}
