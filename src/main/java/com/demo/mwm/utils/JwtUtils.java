package com.demo.mwm.utils;

import com.demo.mwm.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
@Scope(value = "singleton")
public class JwtUtils {
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private int expiration;
    private static final String KEY_CLAIM_USERNAME = "username";
    private static final String KEY_CLAIM_USER_ID = "userId";


    /**
     * Generates a JWT token based on user information.
     *
     * @param user the UserEntity object containing user information.
     * @return the JWT token string.
     * @throws IllegalArgumentException if the user parameter is null.
     */
    public String generateToken(UserEntity user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("Param is null");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put(KEY_CLAIM_USERNAME, user.getUsername());
        claims.put(KEY_CLAIM_USER_ID, user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token string.
     * @return the username extracted from the token.
     * @throws IllegalArgumentException if the token parameter is null.
     */
    public String extractUsername(String token) {
        if (Objects.isNull(token)) {
            throw new IllegalArgumentException("Param is null");
        }
        return this.extractClaim(token, Claims::getSubject);
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


//    public boolean isTokenExpired(String token) {
//        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
//        return expirationDate.before(new Date());
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        if (Objects.isNull(token) || Objects.isNull(userDetails)) {
//            throw new IllegalArgumentException("Param is null");
//        }
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()));
//    }

}
