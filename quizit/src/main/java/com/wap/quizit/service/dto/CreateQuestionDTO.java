package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
public class CreateQuestionDTO {

  @NotNull Long quiz;
  @NotNull @NotBlank String contents;
  @NotNull Boolean isMultipleChoice;
  @NotNull Boolean isClosed;
  @NotNull List<CreateAnswerDTO> answers;
}
