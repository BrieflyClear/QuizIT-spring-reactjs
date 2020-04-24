package com.wap.quizit.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuizQuestionFileDTO {

  @NotNull @NotBlank String contents;
  @NotNull Boolean isMultipleChoice;
  @NotNull Boolean isClosed;
  @NotNull List<CreateAnswerDTO> answers;
}
