package mystic.conduit.domain.user.service;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.user.dto.UpdateDto;
import mystic.conduit.domain.user.dto.UserDto;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.exception.EmailTakenException;
import mystic.conduit.exception.UserNotFoundException;
import mystic.conduit.exception.UsernameTakenException;
import mystic.conduit.shared.mapper.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getCurrentUser(AuthUserDetails auth) {
        String email = auth.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return mapper.mapToUserResponse(userEntity);
    }

    @Override
    public UserDto updateUser(UpdateDto user, AuthUserDetails auth) {
        String email = auth.getEmail();

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (user.getEmail() != null) {
            userRepository.findByEmail(user.getEmail()).filter(found -> found.getId().equals(userEntity.getId())).ifPresent(found -> {
                throw new EmailTakenException();
            });
            userEntity.setEmail(user.getEmail());
        }

        if (user.getUsername() != null) {
            userRepository.findByUsername(user.getUsername()).filter(found -> found.getId().equals(userEntity.getId())).ifPresent(found -> {
                throw new UsernameTakenException();
            });
            userEntity.setUsername(user.getUsername());
        }

        if (user.getImage() != null) {
            userEntity.setImage(user.getImage());
        }

        if (user.getBio() != null) {
            userEntity.setBio(user.getBio());
        }

        if (user.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(userEntity);

        return mapper.mapToUserResponse(userEntity);
    }
}
