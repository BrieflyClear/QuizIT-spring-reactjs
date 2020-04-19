package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CategoryDTO {

  Long id;
  String name;
}
