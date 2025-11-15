package org.example.usermanagmentservice.Service.securityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.example.usermanagmentservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
@Service
public class TokenService {

    @Value("${app.jwt.access-token-expiration-ms}")
    private long accessTokenExpiryMs;

    @Value("${app.jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpiryMs;

    public String createAccessToken(String username) {
        return JwtUtil.generateToken(username, accessTokenExpiryMs, JwtUtil.getPrivateKey());
    }

    public String createRefreshToken(String username) {
        return JwtUtil.generateToken(username, refreshTokenExpiryMs, JwtUtil.getPrivateKey());
    }

    public Jws<Claims> parseToken(String token) {
        PublicKey publicKey = JwtUtil.getPublicKey();
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
    }
    public String getUsernameFromToken(String token) {
        try {
            return parseToken(token).getBody().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token istifadəçi adı çıxarıla bilmədi.", e);
        }
    }
    public boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch ( RuntimeException ex) {
            return false;
        }
    }

}
