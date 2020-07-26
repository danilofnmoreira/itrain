package com.itrain.security.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JWSUtil {

    public static final long EXPIRATION_TIME = 860_000_000;
    public static final String SECRET = "2dsEfp6YxM+sNThIwBbTY/86uoQ81KtcTDjLKWCcRg/06ZXNxON0TkHsIYX+jophjGfBwm2wgJHzJtOlnixfaQ==";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static String createJws(UserDetails user) {

        return Jwts
            .builder()
            .setSubject(user.getUsername())
            .claim("rol", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(getSignKey())
            .compact();
    }

    public static Key getSignKey() {

        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public static Claims parseJws(String jws) {

        return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(jws)
            .getBody();
    }

    public static String getSubject(Claims claims) {
        return claims.getSubject();
    }

    @SuppressWarnings(value = { "unchecked" })
    public static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return ((ArrayList<String>) claims.get("rol"))
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

}