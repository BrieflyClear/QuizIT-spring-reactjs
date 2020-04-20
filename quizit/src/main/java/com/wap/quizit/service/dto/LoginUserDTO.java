package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class LoginUserDTO {

  @NotNull @NotBlank
  @Email
  @Size(min = 5, max = 254)
  String email;

  @NotBlank @NotNull String password;
}
