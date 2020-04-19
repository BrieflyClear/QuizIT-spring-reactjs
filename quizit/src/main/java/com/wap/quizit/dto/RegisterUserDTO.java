package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegisterUserDTO {

  String username;
  String email;
  String password;
  Long role;
}
