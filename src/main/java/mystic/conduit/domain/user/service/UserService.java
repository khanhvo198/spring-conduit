package mystic.conduit.domain.user.service;

import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.user.dto.UpdateDto;
import mystic.conduit.domain.user.dto.UserDto;

public interface UserService {
    UserDto getCurrentUser(AuthUserDetails auth);

    UserDto updateUser(UpdateDto user, AuthUserDetails auth);
}
