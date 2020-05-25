package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wap.quizit.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;

@Value
@JsonDeserialize(builder = RegisterUserDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
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

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
