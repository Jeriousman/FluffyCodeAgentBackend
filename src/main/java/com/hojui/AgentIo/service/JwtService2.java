package com.hojui.AgentIo.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// JwtService와 같은 일을 하지만, 비밀키를 매번 새로 만들지 않고 .env의 JWT_SECRET에서 읽는다.
// → 서버를 재시작해도 같은 키를 쓰므로, 이전에 발급한 토큰이 계속 유효하다.
// 공개 메서드(generateToken, extractUserName, validateToken)는 JwtService와 같아서
// JwtFilter, AuthService에서 타입만 JwtService2로 바꾸면 그대로 교체된다.
@Service
public class JwtService2 {

    // 토큰에 서명(도장)하고 검증할 때 쓰는 비밀키. 서버가 켜질 때 한 번 만들어지고 바뀌지 않는다.
    private final SecretKey key;

    // 토큰 유효 시간 (밀리초)
    private final long expirationMs;

    // @Value("${JWT_SECRET}")
    //   application.properties의 spring.config.import 설정으로 .env가 함께 읽히므로,
    //   .env의 JWT_SECRET 값이 여기로 들어온다. (OS 환경변수 JWT_SECRET이 있으면 그 값이 우선)
    //   기본값이 없으므로 JWT_SECRET이 없으면 서버가 시작되지 않는다 → 키 없이 조용히 실행되는 사고 방지.
    // @Value("${security.jwt.expiration-minutes:60}")
    //   설정이 없으면 기본 60분.
    public JwtService2(@Value("${JWT_SECRET}") String secret,
                       @Value("${security.jwt.expiration-minutes:60}") long expirationMinutes) {

        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET이 비어 있습니다. .env에 openssl rand -base64 32 결과를 넣으세요.");
        }

        // .env의 Base64 문자열 → 원래의 32바이트 → HS256 서명용 키 객체
        // 32바이트(256비트) 미만이면 jjwt가 WeakKeyException을 던져 서버가 시작되지 않는다.
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationMs = expirationMinutes * 60_000;
    }

    // 로그인 성공 시 호출: username을 담고 비밀키로 서명한 토큰을 만들어 돌려준다.
    public String generateToken(String username) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(username)                         // "sub": 누구의 토큰인지
                .issuedAt(new Date(now))                   // "iat": 발급 시각
                .expiration(new Date(now + expirationMs))  // "exp": 만료 시각
                .signWith(key)                             // 비밀키로 서명 → 토큰의 세 번째 부분(signature)
                .compact();
    }

    // 토큰에서 사용자 이름("sub")을 꺼낸다.
    // 서명이 틀리거나 만료된 토큰이면 JwtException이 발생한다.
    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰이 이 사용자의 것인지 확인한다.
    // 서명과 만료 검사는 extractAllClaims(parseSignedClaims)에서 이미 하므로 여기서는 사용자 일치만 본다.
    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUserName(token).equals(userDetails.getUsername());
    }

    // 같은 비밀키로 서명을 다시 계산해 토큰의 서명과 비교하고, 만료 시각도 확인한 뒤 내용(payload)을 꺼낸다.
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
