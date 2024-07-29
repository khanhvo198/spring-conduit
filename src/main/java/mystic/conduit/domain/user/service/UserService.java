package mystic.conduit.domain.user.service;

import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.user.dto.UpdateDto;
import mystic.conduit.domain.user.dto.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDto getCurrentUser();
    UserDto updateUser(UpdateDto user);
}
