package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@JsonDeserialize(builder = UserQuizSummary.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserQuizSummary {

  @NotNull Long attemptId;
  @NotNull Long user;
  @NotNull Long quiz;
  @NotNull Integer maxPointsCount;
  @NotNull Integer pointsGained;
  @NotNull String attemptTime;
  @NotNull List<UserQuizQuestionSummary> questions;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
