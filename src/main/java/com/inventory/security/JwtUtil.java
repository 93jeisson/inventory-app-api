package com.inventory.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mi_clave_super_segura_12345678901234567890";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // 🔐 GENERAR TOKEN (AHORA CON ROL)
    public String generarToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol) // 👈 IMPORTANTE
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey())
                .compact();
    }

    // 🔍 EXTRAER EMAIL (BIEN)
    public String extraerEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 👈 AQUÍ ESTABA EL ERROR
    }

    // 🔥 EXTRAER ROL (NUEVO)
    public String extraerRol(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("rol", String.class);
    }
}