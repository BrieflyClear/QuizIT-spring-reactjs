package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginUserDTO {

  String email;
  String password;
}
