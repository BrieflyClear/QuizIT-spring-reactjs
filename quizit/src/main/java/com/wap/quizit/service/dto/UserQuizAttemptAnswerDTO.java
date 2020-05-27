package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@JsonDeserialize(builder = UserQuizAttemptAnswerDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserQuizAttemptAnswerDTO {

  @NotNull Long id;
  @NotNull Long attempt;
  @NotNull Long question;
  Long answerGiven;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
