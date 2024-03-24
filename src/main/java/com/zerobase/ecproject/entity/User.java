package com.zerobase.ecproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collection;
import javax.management.relation.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // 사용자 권한 반환 로직 구현

    return null;
  }

  @Override
  public String getPassword() {
    // 사용자 비밀번호 반환 로직 구현

    return null;
  }

  @Override
  public String getUsername() {
    // 사용자 이름 반환 로직 구현

    return null;
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
