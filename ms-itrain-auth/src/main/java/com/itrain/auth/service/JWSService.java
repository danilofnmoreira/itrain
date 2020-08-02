package com.itrain.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Service
@ConfigurationProperties(prefix = "itrain.jws")
public class JWSService {

    public static final String TOKEN_PREFIX = "Bearer ";

    @Getter
    private Key signKey;

    @Setter
    @Getter
    private long expirationTime;

    void setSecret(String secret) {
        this.signKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createJws(UserDetails user) {

        return Jwts
            .builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSignKey())
            .compact();
    }

    public Claims parseJws(String jws) {

        return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(jws)
            .getBody();
    }

    public String getSubject(Claims claims) {
        return claims.getSubject();
    }

}