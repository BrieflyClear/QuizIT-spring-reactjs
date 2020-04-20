package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
public class QuestionDTO {

  @NotNull Long id;

  @NotNull Long quiz;

  @NotNull @NotBlank String contents;

  @NotNull List<Long> answers;

  @NotNull List<Long> comments;
}
