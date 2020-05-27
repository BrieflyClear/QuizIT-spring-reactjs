package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@JsonDeserialize(builder = CategoryDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class CategoryDTO {

  @NotNull Long id;

  @NotNull @NotBlank String name;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
