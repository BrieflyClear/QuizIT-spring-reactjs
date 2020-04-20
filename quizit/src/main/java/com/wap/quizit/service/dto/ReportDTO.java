package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class ReportDTO {

  @NotNull Long id;

  @NotNull @NotBlank String title;

  @NotNull @NotBlank String description;

  @NotNull @NotBlank String status;

  @NotNull @NotBlank String issuedTime;

  @NotNull Long reportedQuiz;

  @NotNull Long reportingUser;
}
