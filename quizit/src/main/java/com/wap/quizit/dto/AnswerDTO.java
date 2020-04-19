package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AnswerDTO {

  Long id;
  String contents;
  boolean isCorrect;
  Long question;
}
