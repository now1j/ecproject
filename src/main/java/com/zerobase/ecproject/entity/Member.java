package com.zerobase.ecproject.entity;

import com.zerobase.ecproject.security.MemberRole;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
public class Member implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING) // 열거형 값을 문자열로 저장
  private List<MemberRole> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.name()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    // 계정 만료 여부 로직 구현
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    // 계정 잠금 여부 로직 구현
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // 인증 정보 만료 여부 로직 구현
    return false;
  }

  @Override
  public boolean isEnabled() {
    // 계정 활성 상태 여부 로직 구현
    return false;
  }
}
