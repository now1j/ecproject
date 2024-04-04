package com.zerobase.ecproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Auth {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SignUp {

    private String username;
    private String password;
    private String role;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SignIn {

    private String username;
    private String password;

  }
}