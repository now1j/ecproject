package com.zerobase.ecproject.controller;

import com.zerobase.ecproject.dto.Auth;
import com.zerobase.ecproject.security.TokenProvider;
import com.zerobase.ecproject.service.MemberService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final MemberService memberService;
  private final TokenProvider tokenProvider;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
    var result = this.memberService.register(request);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
    var member = this.memberService.authenticate(request);
    // MemberRole 열거형 리스트를 문자열 리스트로 변환
    var rolesAsString = member.getRoles().stream()
        .map(Enum::name)
        .collect(Collectors.toList());
    var token = this.tokenProvider.generateToken(member.getUsername(), rolesAsString);
    return ResponseEntity.ok(token);
  }
}
