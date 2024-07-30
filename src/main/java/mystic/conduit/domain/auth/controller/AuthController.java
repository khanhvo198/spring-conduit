package mystic.conduit.domain.auth.controller;


import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.dto.LoginDto;
import mystic.conduit.domain.auth.dto.RegistrationDto;
import mystic.conduit.domain.auth.service.AuthService;
import mystic.conduit.domain.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/users")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto user) {
        return ResponseEntity.ok(authService.login(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> registration(@RequestBody RegistrationDto user) {
        return ResponseEntity.ok(authService.registration(user));
    }

}
