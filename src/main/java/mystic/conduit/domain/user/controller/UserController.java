package mystic.conduit.domain.user.controller;


import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.user.dto.UpdateDto;
import mystic.conduit.domain.user.dto.UserDto;
import mystic.conduit.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(userService.getCurrentUser(auth));
    }


    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateDto user, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(userService.updateUser(user, auth));
    }


}
