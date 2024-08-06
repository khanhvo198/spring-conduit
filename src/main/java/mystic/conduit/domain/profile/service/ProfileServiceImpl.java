package mystic.conduit.domain.profile.service;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.profile.dto.ProfileDto;
import mystic.conduit.domain.profile.mapper.ProfileMapper;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    @Override
    public ProfileDto getProfile(String username, AuthUserDetails auth) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        boolean isFollowing = false;
        if (auth != null) {
            isFollowing = userRepository.findByFollowerId(auth.getId()).stream().map(UserEntity::getId).anyMatch(following -> following.equals(user.getId()));
        }

        return profileMapper.mapToProfileDto(user, isFollowing);
    }

    @Override
    public ProfileDto followProfile(String username, AuthUserDetails auth) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        UserEntity currentUser = userRepository.findById(auth.getId()).orElseThrow(UserNotFoundException::new);
        currentUser.getFollowing().add(user);
        userRepository.save(currentUser);
        return profileMapper.mapToProfileDto(user, true );
    }

    @Override
    public ProfileDto unfollowProfile(String username, AuthUserDetails auth) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        UserEntity currentUser = userRepository.findById(auth.getId()).orElseThrow(UserNotFoundException::new);
        currentUser.getFollowing().remove(user);
        userRepository.save(currentUser);
        return profileMapper.mapToProfileDto(user, false);

    }
}
