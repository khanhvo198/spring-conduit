package mystic.conduit.domain.auth.service;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        return userEntity
                .map(user -> AuthUserDetails
                        .builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .build())
                .orElse(null);
    }
}
