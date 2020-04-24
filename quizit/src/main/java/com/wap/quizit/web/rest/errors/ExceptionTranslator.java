package com.wap.quizit.web.rest.errors;

import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.exception.InvalidPasswordException;
import com.wap.quizit.service.exception.UserNotExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
    Error apiError = new Error(NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(UserNotExistsException.class)
  protected ResponseEntity<Object> handleUserNotExists(UserNotExistsException ex, WebRequest request) {
    Error apiError = new Error(NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  protected ResponseEntity<Object> handleInvalidPassword(InvalidPasswordException ex, WebRequest request) {
    Error apiError = new Error(UNAUTHORIZED);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(EntityFieldValidationException.class)
  protected ResponseEntity<Object> handleRejectedValue(EntityFieldValidationException ex, WebRequest request) {
    Error error = new Error(BAD_REQUEST);
    error.setMessage("path " + request.getContextPath());
    ApiValidationError apiError = new ApiValidationError(ex.getObject(), ex.getField(), ex.getRejectedValue(), ex.getMessage());
    error.setSubErrors(List.of(apiError));
    return buildResponseEntity(error);
  }

  @ExceptionHandler(IOException.class)
  protected ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
    Error error = new Error(BAD_REQUEST);
    error.setMessage("Could not parse Json file! Internal message: " + ex.getMessage());
    return buildResponseEntity(error);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                HttpHeaders headers, HttpStatus status,
                                                                WebRequest request) {
    String error = "Malformed JSON request";
    return buildResponseEntity(new Error(HttpStatus.BAD_REQUEST, error, ex));
  }

  private ResponseEntity<Object> buildResponseEntity(Error apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
