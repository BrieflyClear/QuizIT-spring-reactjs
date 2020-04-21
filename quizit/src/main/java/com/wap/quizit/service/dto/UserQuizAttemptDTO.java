package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
public class UserQuizAttemptDTO {

  @NotNull Long id;
  @NotNull Long user;
  @NotNull Long quiz;
  @NotNull String attemptTime;
  @NotNull List<Long> attemptAnswers;
}
