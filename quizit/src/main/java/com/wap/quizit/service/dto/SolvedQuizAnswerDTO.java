package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class SolvedQuizAnswerDTO {

  @NotNull Long id;
  @NotNull Long user;
  @NotNull Long question;
  @NotNull Long answerGiven;
}
