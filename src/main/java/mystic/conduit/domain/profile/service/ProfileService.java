package mystic.conduit.domain.profile.service;

import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.profile.dto.ProfileDto;
import mystic.conduit.domain.profile.dto.SingleProfileDto;

import java.util.Optional;

public interface ProfileService {
    SingleProfileDto getProfile(String username, AuthUserDetails auth);

    SingleProfileDto followProfile(String username, AuthUserDetails auth);

    SingleProfileDto unfollowProfile(String username, AuthUserDetails auth);
}
