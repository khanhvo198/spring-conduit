package mystic.conduit.domain.profile.service;

import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.profile.dto.ProfileDto;

import java.util.Optional;

public interface ProfileService {
    ProfileDto getProfile(String username, AuthUserDetails auth);

    ProfileDto followProfile(String username, AuthUserDetails auth);

    ProfileDto unfollowProfile(String username, AuthUserDetails auth);
}
