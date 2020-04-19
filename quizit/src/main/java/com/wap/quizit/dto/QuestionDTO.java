package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class QuestionDTO {

  Long id;
  Long quiz;
  String contents;
  List<Long> answers;
  List<Long> comments;
}
