package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentDTO {

  Long id;
  String contents;
  String issuedTime;
  Long author;
  Long question;
}
