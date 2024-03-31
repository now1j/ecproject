package com.zerobase.ecproject.service;

import com.zerobase.ecproject.dto.Auth.SignIn;
import com.zerobase.ecproject.dto.Auth.SignUp;
import com.zerobase.ecproject.entity.Member; // 사용자 정의 Member 엔티티 올바른 임포트
import com.zerobase.ecproject.repository.MemberRepository;
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
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))))
        .orElseThrow(() -> new UsernameNotFoundException("Member '" + username + "'가 존재하지 않습니다."));
  }

  public Member register(SignUp signUpRequest) {
    if (memberRepository.existsByUsername(signUpRequest.getUsername())) {
      throw new RuntimeException("Error: Username is already taken.");
    }

    Member member = new Member();
    member.setUsername(signUpRequest.getUsername());
    member.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    member.setRoles(Collections.singletonList("ROLE_MEMBER"));

    return memberRepository.save(member);
  }

  public Member authenticate(SignIn signInRequest) {
    Member member = memberRepository.findByUsername(signInRequest.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(
            "User Not Found with username: " + signInRequest.getUsername()));

    if (!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
      throw new RuntimeException("Error: Incorrect password.");
    }

    return member;
  }
}
