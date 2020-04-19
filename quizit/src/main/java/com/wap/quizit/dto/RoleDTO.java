package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleDTO {

  Long id;
  String name;
}
