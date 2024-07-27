package mystic.conduit.domain.auth.service;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.dto.LoginDto;
import mystic.conduit.domain.auth.dto.RegistrationDto;
import mystic.conduit.domain.user.dto.UserDto;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.exception.AppException;
import mystic.conduit.exception.Error;
import mystic.conduit.shared.Mapper;
import mystic.conduit.utils.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final Mapper mapper;

    @Override
    public UserDto login(LoginDto user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail())
                                    .filter(candidate -> passwordEncoder.matches(user.getPassword(), candidate.getPassword()))
                                    .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        return mapper.convertEntityToUserDto(userEntity);
    }

    @Override
    public UserDto registration(RegistrationDto user) {
        userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername()).stream().findAny().ifPresent(userEntity -> { throw new AppException(Error.USER_NOT_FOUND); });

        UserEntity userEntity = UserEntity.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .bio("")
                .image("")
                .build();

        userRepository.save(userEntity);

        return mapper.convertEntityToUserDto(userEntity);
    }
}
