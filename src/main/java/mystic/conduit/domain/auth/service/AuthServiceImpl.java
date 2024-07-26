package mystic.conduit.domain.auth.service;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.dto.LoginDto;
import mystic.conduit.domain.auth.dto.RegistrationDto;
import mystic.conduit.domain.auth.dto.UserDto;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper mapper;

    @Override
    public UserDto login(LoginDto user) {
        UserEntity userEntity = userRepository
                                    .findByEmail(user.getEmail())
                .filter(candidate -> passwordEncoder.matches(user.getPassword(), candidate.getPassword()))
                .orElse(null);

        return convertToUserDto(userEntity);
    }

    @Override
    public UserDto registration(RegistrationDto user) {
        Optional<UserEntity> existUser = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());

        if (existUser.isPresent()) {
            return null;
        }

        System.out.println(user);
        UserEntity userEntity = UserEntity.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .bio("")
                .image("")
                .build();

        userRepository.save(userEntity);

        return convertToUserDto(userEntity);
    }

    private UserDto convertToUserDto (UserEntity userEntity) {
        UserDto response = mapper.map(userEntity, UserDto.class);
        response.setToken(jwtUtils.encode(response.getEmail()));
        return response;
    }


}
