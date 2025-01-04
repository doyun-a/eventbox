package com.kakaologin.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 반드시 환경 변수로 관리

    public static String generateToken(Map<String, Object> claims, long expirationMillis) {
        return Jwts.builder()
                .setClaims(claims) // 사용자 정보 (Payload)
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis)) // 만료 시간
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 서명
                .compact();
    }
}
