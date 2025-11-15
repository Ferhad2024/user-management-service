package org.example.usermanagmentservice.Service.securityService;

import lombok.RequiredArgsConstructor;
import org.example.usermanagmentservice.Mapper.UserResponseRequestMapper;
import org.example.usermanagmentservice.entity.User;
import org.example.usermanagmentservice.repository.UserRepository;
import org.example.usermanagmentservice.request.AuthRequest;
import org.example.usermanagmentservice.request.UserRequest;
import org.example.usermanagmentservice.response.TokenResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserResponseRequestMapper mapper;
    public TokenResponse register(UserRequest request) {

        User user= mapper.convertUser(request);
        String access = tokenService.createAccessToken(request.username());
        String refresh = tokenService.createRefreshToken(request.username());
        userRepository.save(user);
        return new TokenResponse(access, refresh);
    }

    public TokenResponse authenticate(AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        String access = tokenService.createAccessToken(authRequest.username());
        String refresh = tokenService.createRefreshToken(authRequest.username());
        return new TokenResponse(access, refresh);
    }

    public TokenResponse refreshTokens(String refreshToken) {
        if (!tokenService.isTokenValid(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        String username = tokenService.getUsernameFromToken(refreshToken);
        String newAccess = tokenService.createAccessToken(username);
        String newRefresh = tokenService.createRefreshToken(username);
        return new TokenResponse(newAccess, newRefresh);
    }
}
