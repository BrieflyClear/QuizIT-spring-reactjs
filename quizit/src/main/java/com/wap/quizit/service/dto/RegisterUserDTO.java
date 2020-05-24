package com.wap.quizit.service.dto;

import com.wap.quizit.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;

@Value
@Builder
public class RegisterUserDTO {

  @NotNull
  @NotBlank
  @Pattern(regexp = Constants.USERNAME_REGEX)
  @Size(min = 3, max = 50)
  String username;

  @NotNull
  @NotBlank
  @Size(min = 5, max = 254)
  String email;

  @NotNull
  @NotBlank
  @Size(min = 3, max = 50)
  String password;
}
