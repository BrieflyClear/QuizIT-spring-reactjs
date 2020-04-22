package com.wap.quizit.service.exception;

public class AttemptAnswerNotMatchingQuestion extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public AttemptAnswerNotMatchingQuestion(Long attemptAnswerId, Long questionId) {
    super("Attempt's answer [ID "+attemptAnswerId+"] not matching question ["+questionId+"]!");
  }
}
