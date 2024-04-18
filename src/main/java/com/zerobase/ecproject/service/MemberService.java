package com.zerobase.ecproject.service;

import com.zerobase.ecproject.dto.Auth.SignIn;
import com.zerobase.ecproject.dto.Auth.SignUp;
import com.zerobase.ecproject.entity.Member; // 사용자 정의 Member 엔티티 올바른 임포트
import com.zerobase.ecproject.repository.MemberRepository;
import com.zerobase.ecproject.security.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return memberRepository.findByUsername(username)
        .map(member -> new User(member.getUsername(), member.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()))))
        .orElseThrow(() -> new UsernameNotFoundException("사용자 '" + username + "'를 찾을 수 없습니다."));
  }

  // 회원가입 처리 메서드
  public Member register(SignUp signUpRequest) {
    if (memberRepository.existsByUsername(signUpRequest.getUsername())) {
      throw new RuntimeException("오류: 이미 사용 중인 사용자 이름입니다.");
    }

    Member member = new Member();
    member.setUsername(signUpRequest.getUsername());
    member.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

    // signUpRequest에서 받은 String 타입의 role을 MemberRole 열거형으로 변환
    try {
      MemberRole selectedRole = MemberRole.valueOf(signUpRequest.getRole().toUpperCase());
      member.setRole(selectedRole); // 변환된 역할 설정
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("역할이 유효하지 않습니다.");
    }

    return memberRepository.save(member);
  }

  // 사용자 로그인 처리 메서드
  public Member authenticate(SignIn signInRequest) {
    Member member = memberRepository.findByUsername(signInRequest.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("해당 ID를 찾을 수 없습니다."));

    if (!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
      throw new RuntimeException("비밀번호가 틀렸습니다.");
    }

    return member;
  }
}
