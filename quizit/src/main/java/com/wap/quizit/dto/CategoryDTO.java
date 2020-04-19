package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDTO {

  Long id;
  String name;
}
