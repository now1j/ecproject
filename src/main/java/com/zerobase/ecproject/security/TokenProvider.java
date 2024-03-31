package com.zerobase.ecproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProvider {

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long expiration;

  public String generateToken(String username, List<String> roles) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + expiration);

    Claims claims = Jwts.claims().setSubject(username);   // 'subject'를 설정하여 토큰의 주체를 지정
    claims.put("roles", roles);  // 사용자의 역할 정보를 'roles' 클레임에 추가

    return Jwts.builder()
        .setClaims(claims)          // 토큰의 주체
        .setIssuedAt(now)           // 토큰 발행 시간
        .setExpiration(validity)    // 토큰 만료 시간
        .signWith(SignatureAlgorithm.HS256, secretKey)    // 서명 알고리즘 및 비밀 키 사용
        .compact();                 // JWT 생성 및 반환
  }
}
