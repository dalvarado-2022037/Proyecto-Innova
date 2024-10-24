package org.douglasalvarado.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.douglasalvarado.config.JWTConfig;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTUtil {

    private final JWTConfig jwtConfig;

    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(jwtConfig.getLlave().getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
