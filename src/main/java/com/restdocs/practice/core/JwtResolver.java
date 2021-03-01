package com.restdocs.practice.core;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtResolver {

    private static final byte[] KEY = "rest-docs-practice".getBytes();

    public String create(){
        Instant instant = LocalDateTime.now()
                .plusDays(5)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Date expired = new Date(instant.toEpochMilli());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("auth", "Authorization")
                .claim("test", 1)
                .setExpiration(expired)
                .setIssuedAt(new Date())
                .setIssuer("ndgndg91")
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public Claims parseToClaims(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }
}
