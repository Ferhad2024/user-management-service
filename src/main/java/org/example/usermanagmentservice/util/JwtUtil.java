package org.example.usermanagmentservice.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private static final String PRIVATE_KEY_PATH = "keys/private_pkcs8.pem";
    private static final String PUBLIC_KEY_PATH = "keys/public_key.pem";

    private static final PrivateKey PRIVATE_KEY = loadPrivateKey();
    private static final PublicKey PUBLIC_KEY = loadPublicKey();

    private static byte[] parsePEMKey(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            throw new FileNotFoundException("Key URl Not Found: " + path);
        }

        try (InputStream inputStream = resource.getInputStream()) {
            String key = new String(inputStream.readAllBytes());
            key = key.replaceAll("-----BEGIN (.*)-----", "")
                    .replaceAll("-----END (.*)-----", "")
                    .replaceAll("\\s", "");
            return Base64.getDecoder().decode(key);
        }
    }

    private static PrivateKey loadPrivateKey() {
        try {
            byte[] keyBytes = parsePEMKey(PRIVATE_KEY_PATH);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Private key yüklənərkən kritik xəta baş verdi.", e);
        }
    }

    private static PublicKey loadPublicKey() {
        try {
            byte[] keyBytes = parsePEMKey(PUBLIC_KEY_PATH);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Public key yüklənərkən kritik xəta baş verdi.", e);
        }
    }

    public static PrivateKey getPrivateKey() {
        return PRIVATE_KEY;
    }

    public static PublicKey getPublicKey() {
        return PUBLIC_KEY;
    }

    public static String generateToken(String username, long expirationMillis, PrivateKey privateKey) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public static String generateAccessToken(String username, long expirationMillis) {
        return generateToken(username, expirationMillis, getPrivateKey());
    }

    public static String generateRefreshToken(String username, long expirationMillis) {
        return generateToken(username, expirationMillis, getPrivateKey());
    }
    public static Claims extractClaims(String token) {
        PublicKey publicKey = getPublicKey();
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public static boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

}
