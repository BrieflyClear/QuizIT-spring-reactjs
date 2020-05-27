package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@JsonDeserialize(builder = QuestionDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class QuestionDTO {

  @NotNull Long id;

  @NotNull Long quiz;

  @NotNull @NotBlank String contents;

  @NotNull Boolean isMultipleChoice;

  @NotNull Boolean isClosed;

  @NotNull List<Long> answers;

  @NotNull List<Long> comments;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
