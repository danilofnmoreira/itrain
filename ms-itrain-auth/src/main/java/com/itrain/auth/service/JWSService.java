package com.itrain.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.itrain.auth.domain.User;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    void setSecret(final String secret) {
        this.signKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createJws(final User user) {

        return Jwts
            .builder()
            .setSubject(user.getUsername())
            .claim("rol", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .claim("uid", user.getId())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSignKey())
            .compact();
    }

    public Claims parseJws(final String jws) {

        return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(jws)
            .getBody();
    }

    public String getSubject(final Claims claims) {
        return claims.getSubject();
    }

    public Long getUserId(final Claims claims) {
        return Long.parseLong(claims.get("uid").toString());
    }

    @SuppressWarnings(value = { "unchecked" })
    public List<SimpleGrantedAuthority> getAuthorities(final Claims claims) {
        return ((ArrayList<String>) claims.get("rol"))
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

}