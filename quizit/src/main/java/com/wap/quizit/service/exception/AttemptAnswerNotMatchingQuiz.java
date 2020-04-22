package com.wap.quizit.service.exception;

public class AttemptAnswerNotMatchingQuiz extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public AttemptAnswerNotMatchingQuiz(Long attemptAnswerId, Long attemptId) {
    super("Attempt's answer [ID "+attemptAnswerId+"] not matching quiz from attempt ["+attemptId+"]!");
  }
}
