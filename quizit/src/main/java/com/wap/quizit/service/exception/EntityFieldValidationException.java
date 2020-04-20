package com.wap.quizit.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EntityFieldValidationException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private String object;
  private String field;
  private Object rejectedValue;
  private String message;
}
