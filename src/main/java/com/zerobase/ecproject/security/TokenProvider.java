package com.zerobase.ecproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    // 토큰에서 claims를 추출
    Claims claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();

    // claims에서 사용자 이름(주체)을 추출
    String username = claims.getSubject();

    // claims에서 권한 정보를 추출하여 GrantedAuthority 리스트로 변환
    @SuppressWarnings("unchecked")
    List<String> roles = claims.get("roles", List.class);
    Collection<? extends GrantedAuthority> authorities =
        roles.stream()
            .map(role -> new SimpleGrantedAuthority(role))
            .collect(Collectors.toList());

    // 인증 객체 생성
    UserDetails principal = new User(username, "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }
}