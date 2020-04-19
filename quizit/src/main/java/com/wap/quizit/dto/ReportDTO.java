package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReportDTO {

  Long id;
  String title;
  String description;
  String status;
  String issuedTime;
  Long reportedQuiz;
  Long reportingUser;
}
