package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class QuizDTO {

  Long id;
  Boolean isPublic;
  String title;
  Long author;
  List<Long> categories;
  List<Long> questions;
  List<Long> reportsIssued;
}
