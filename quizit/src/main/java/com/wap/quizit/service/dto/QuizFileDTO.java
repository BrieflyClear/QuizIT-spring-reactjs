package com.wap.quizit.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuizFileDTO {

  @NotNull Boolean isPublic;

  @NotNull @NotBlank String title;

  @NotNull List<String> categories;

  @NotNull List<QuizQuestionFileDTO> questions;
}
