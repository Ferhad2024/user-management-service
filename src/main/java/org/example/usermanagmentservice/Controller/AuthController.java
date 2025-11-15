package org.example.usermanagmentservice.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.usermanagmentservice.Service.securityService.AuthService;
import org.example.usermanagmentservice.request.AuthRequest;
import org.example.usermanagmentservice.request.TokenRefreshRequest;
import org.example.usermanagmentservice.request.UserRequest;
import org.example.usermanagmentservice.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://txt-dowland.netlify.app")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody @Valid UserRequest req) {
        TokenResponse resp = authService.register(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthRequest req) {
        TokenResponse resp = authService.authenticate(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody TokenRefreshRequest req) {
        TokenResponse resp = authService.refreshTokens(req.refreshtoken());
        return ResponseEntity.ok(resp);
    }
}
