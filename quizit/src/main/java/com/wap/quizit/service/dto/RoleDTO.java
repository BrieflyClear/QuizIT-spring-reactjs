package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class RoleDTO {

  @NotNull Long id;

  @NotNull @NotBlank String name;
}
