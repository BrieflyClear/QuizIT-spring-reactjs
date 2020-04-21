package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class UserQuizAttemptAnswerDTO {

  @NotNull Long id;
  @NotNull Long attempt;
  @NotNull Long question;
  @NotNull Long answerGiven;
}
