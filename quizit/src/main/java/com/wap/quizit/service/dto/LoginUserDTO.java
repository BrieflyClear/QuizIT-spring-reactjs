package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@JsonDeserialize(builder = LoginUserDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class LoginUserDTO {

  @NotNull @NotBlank
  @Email
  @Size(min = 5, max = 254)
  String email;

  @NotBlank @NotNull String password;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
