package com.railPic.picTrail.config.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtProvider {

    // 개선 포인트 1: 상수 분리 - 매직 넘버 대신 의미 있는 상수 사용으로 가독성 향상
    private static final long ONE_HOUR_IN_MILLIS = 1000 * 60 * 60; // 1시간을 밀리초로 정의
    private static final long EXPIRATION_TIME = ONE_HOUR_IN_MILLIS; // 토큰 유효 기간 설정

    private final Key signingKey;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        // Base64 디코딩 후, HMAC-SHA 서명 키 생성
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰에서 사용자 이름 추출 메서드
    public String generateToken(String username) {
        Date now = new Date(); // 현재 시간 생성
        return Jwts.builder()
                .setSubject(username) // 토큰 주체(사용자 이름) 설정
                .setIssuedAt(now) // 발행 시간 설정
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME)) // 만료 시간 설정
                // .claim("roles", "USER") // 예: 추가 클레임 (주석 처리된 상태)
                .signWith(signingKey, SignatureAlgorithm.HS256) // HMAC-SHA256으로 서명
                .compact(); // 최종 토큰 문자열 생성
    }

    // 토큰에서 사용자 이름 추출 메서드
    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey) // 서명 검증용 키 설정
                    .build()
                    .parseClaimsJws(token) // 토큰 파싱 및 서명 검증
                    .getBody()
                    .getSubject(); // 사용자 이름 반환
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 JWT 토큰입니다.", e); // 유효성 검증 실패 시 예외 발생
        }
    }

    // 토큰 유효성 검증 메서드
    public boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token); // 사용자 이름 추출
            return (extractedUsername.equals(username) && !isTokenExpired(token)); // 이름 일치 및 만료 여부 확인
        } catch (Exception e) {
            return false; // 예외 발생 시 유효하지 않음으로 처리
        }
    }

    // 토큰 만료 여부 확인 메서드
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date()); // 만료 시간이 현재보다 이전인지 확인
    }

    // 토큰 클레임 추출 메서드
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey) // 서명 검증용 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody(); // 클레임 반환
    }


}
