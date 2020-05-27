package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@JsonDeserialize(builder = CreateQuestionDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class CreateQuestionDTO {

  @NotNull Long quiz;
  @NotNull @NotBlank String contents;
  @NotNull Boolean isMultipleChoice;
  @NotNull Boolean isClosed;
  @NotNull List<CreateAnswerDTO> answers;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
