package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
public class UserQuizQuestionSummary {

  @NotNull Long question;
  @NotNull Integer maxPointsCount;
  @NotNull Integer pointsGained;
  @NotNull List<Long> answersGiven;
}
