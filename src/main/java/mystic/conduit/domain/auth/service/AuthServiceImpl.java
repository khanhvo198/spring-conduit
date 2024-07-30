package mystic.conduit.domain.auth.service;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.dto.LoginDto;
import mystic.conduit.domain.auth.dto.RegistrationDto;
import mystic.conduit.domain.user.dto.UserDto;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.exception.EmailTakenException;
import mystic.conduit.exception.UserNotFoundException;
import mystic.conduit.exception.UsernameTakenException;
import mystic.conduit.shared.Mapper;
import mystic.conduit.utils.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                                    .orElseThrow(UserNotFoundException::new);

        return mapper.convertEntityToUserDto(userEntity);
    }

    @Override
    public UserDto registration(RegistrationDto user) {
        userRepository.findByEmail(user.getEmail()).stream().findAny().ifPresent(userEntity -> { throw new EmailTakenException(); });

        userRepository.findByUsername(user.getUsername()).stream().findAny().ifPresent(userEntity -> { throw new UsernameTakenException();});


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
