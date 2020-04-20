package com.wap.quizit.service.exception;

public class EntityNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(Class clazz, Long id) {
    super(clazz.getSimpleName() + " with ID ["+ id + "] not found!");
  }
}