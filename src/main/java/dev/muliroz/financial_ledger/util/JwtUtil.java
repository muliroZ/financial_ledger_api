package dev.muliroz.financial_ledger.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.validity}")
    private long jwtValidity;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        HashMap<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    private String doGenerateToken(HashMap<String, Object> claims, String subject) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + jwtValidity))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token, String username) {
        final String tokenOwner = getUsernameFromToken(token);
        return tokenOwner.equals(username) && !isTokenExpired(token);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        final Date tokenExpiration = getClaimFromToken(token, Claims::getExpiration);
        return tokenExpiration.before(new Date(System.currentTimeMillis()));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
