package mystic.conduit.domain.user.service;

import mystic.conduit.domain.user.dto.UpdateDto;
import mystic.conduit.domain.user.dto.UserDto;

public interface UserService {
    UserDto getCurrentUser();

    UserDto updateUser(UpdateDto user);
}
