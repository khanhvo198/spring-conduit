package mystic.conduit.domain.auth.service;

import mystic.conduit.domain.auth.dto.LoginDto;
import mystic.conduit.domain.auth.dto.RegistrationDto;
import mystic.conduit.domain.auth.dto.UserDto;

public interface AuthService {
    UserDto login (LoginDto user);
    UserDto registration (RegistrationDto user);
}
