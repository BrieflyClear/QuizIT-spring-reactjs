package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@JsonDeserialize(builder = UserQuizAttemptDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserQuizAttemptDTO {

  @NotNull Long id;
  @NotNull Long user;
  @NotNull Long quiz;
  @NotNull String attemptTime;
  @NotNull List<Long> attemptAnswers;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
