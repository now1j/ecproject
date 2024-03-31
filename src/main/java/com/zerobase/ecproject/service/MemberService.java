package com.zerobase.ecproject.service;

import com.zerobase.ecproject.repository.MemberRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {

  @Autowired    // 의존성 주입, 사용자 정보를 데이터베이스에서 조회할 수 있도록 함
  private MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return memberRepository.findByUsername(username)    // findByUsername 메서드 호출, Optional<User> 객체 반환
        .map(member -> new User(member.getUsername(), member.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))))
        .orElseThrow(() -> new UsernameNotFoundException("Member '" + username + "'가 존재하지 않습니다."));

  }
}
