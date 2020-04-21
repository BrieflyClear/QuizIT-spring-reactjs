package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
public class UserQuizSummary {

  @NotNull Long attemptId;
  @NotNull Long user;
  @NotNull Long quiz;
  @NotNull Integer maxPointsCount;
  @NotNull Integer pointsGained;
  @NotNull String attemptTime;
  @NotNull List<UserQuizQuestionSummary> questions;
}
