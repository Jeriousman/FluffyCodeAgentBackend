package com.hojui.AgentIo.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// JWT 토큰을 만들고(generateToken), 토큰 안의 정보를 꺼내고(extractUserName), 토큰이 유효한지 검사(validateToken)하는 클래스
@Service
public class JwtService {

    // 토큰에 서명할 때 쓰는 비밀키 (Base64 문자열 형태로 보관)
    private String secretKey = "";



    // 서버가 시작될 때 랜덤 비밀키를 한 번 만든다.
    // 주의: 서버를 재시작하면 키가 바뀌므로, 재시작 전에 발급한 토큰은 모두 무효가 된다.
    public JwtService() {
        try {
        KeyGenerator keyGen = KeyGenerator.getInstance(("HmacSHA256")); // HS256 서명용 키 생성기 (256비트 키)
        SecretKey sk = keyGen.generateKey();
        this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded()); // 바이트 배열 키를 문자열로 바꿔서 필드에 저장
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 로그인 성공 시 호출: username을 담은 토큰을 만들어서 돌려준다.
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>(); // 토큰에 추가로 넣을 정보 (지금은 비어 있음)

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)                                                  // "sub": 누구의 토큰인지
                .issuedAt((new Date(System.currentTimeMillis())))                   // "iat": 발급 시각
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 )) // "exp": 만료 시각 = 지금 + 60분 (밀리초 단위)
                .and()
                .signWith(getKey())  // 비밀키로 서명 → 토큰의 세 번째 부분(signature)이 만들어짐
                .compact();  //this generate keys at the end

    }

    // 문자열로 저장해 둔 비밀키를 다시 서명용 키 객체(SecretKey)로 바꾼다.
    // verifyWith()가 SecretKey 타입을 받기 때문에 반환 타입이 SecretKey여야 한다.
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰에서 사용자 이름("sub" 값)을 꺼낸다.
    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject); // Claims::getSubject == claims -> claims.getSubject()
    }

    // 토큰에서 원하는 값 하나를 꺼내는 공통 메서드.
    // claimResolver에 "무엇을 꺼낼지"를 함수로 넘긴다. (예: Claims::getSubject, Claims::getExpiration)
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);  // 토큰의 내용(payload) 전체
        return claimResolver.apply(claims);              // 그중 원하는 값만 꺼내서 반환
    }

    // 토큰을 검증하고 내용(payload) 전체를 꺼낸다. (jjwt 0.12 문법)
    // 서명이 틀리면 SignatureException, 만료됐으면 ExpiredJwtException 예외가 여기서 발생한다.
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())     // 이 비밀키로 서명을 확인
                .build()
                .parseSignedClaims(token) // 토큰 해석 + 서명/만료 검사
                .getPayload();            // 가운데 부분(sub, iat, exp 등)
    }

    // 토큰이 이 사용자의 것이고, 아직 만료되지 않았으면 true
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 만료 시각이 지금보다 이전이면 만료된 것
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 토큰에서 만료 시각("exp" 값)을 꺼낸다.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
