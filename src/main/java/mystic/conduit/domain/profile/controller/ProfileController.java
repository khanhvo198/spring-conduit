package mystic.conduit.domain.profile.controller;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.profile.dto.ProfileDto;
import mystic.conduit.domain.profile.dto.SingleProfileDto;
import mystic.conduit.domain.profile.service.ProfileService;
import mystic.conduit.shared.mapper.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{username}")
    public ResponseEntity<SingleProfileDto> getProfile(@PathVariable String username, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(profileService.getProfile(username, auth));
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<SingleProfileDto> followProfile(@PathVariable String username, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(profileService.followProfile(username, auth));
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<SingleProfileDto> unfollowProfile(@PathVariable String username, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(profileService.unfollowProfile(username, auth));
    }


}
