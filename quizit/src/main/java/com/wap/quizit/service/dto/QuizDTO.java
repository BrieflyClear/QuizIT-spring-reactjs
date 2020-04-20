package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
public class QuizDTO {

  @NotNull Long id;

  @NotNull Boolean isPublic;

  @NotNull @NotBlank String title;

  @NotNull Long author;

  @NotNull List<Long> categories;

  @NotNull List<Long> questions;

  @NotNull List<Long> reportsIssued;
}
