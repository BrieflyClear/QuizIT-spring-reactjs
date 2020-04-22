package com.wap.quizit.util;

import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Question;
import com.wap.quizit.model.User;
import com.wap.quizit.service.exception.EntityFieldValidationException;

import java.util.regex.Pattern;

public class DataValidator {

  private DataValidator() {
  }

  public static void validateUser(User user) {
    validateUsername(user.getUsername());
    validatePassword(user.getPassword());
    validateEmail(user.getEmail());
  }

  public static void validateUsername(String username) {
    if(!Pattern.matches(Constants.USERNAME_REGEX, username)) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "username", username, "Wrong username characters! " +
          "Length must be in range 4-15. Only alphanumeric, '_' and '-' characters are allowed.");
    }
  }

  public static void validatePassword(String password) {
    if(!Pattern.matches(Constants.PASSWORD_REGEX, password)) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "password", "---", "Wrong password characters! " +
          "Length must be in range 6-15. Minimum one lower case and one upper case letter and one number.");
    }
  }

  public static void validateEmail(String email) {
    if(email.length() < Constants.MIN_EMAIL_LENGTH) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "email", email.length(),
          "Email too short! Minimum "+Constants.MIN_EMAIL_LENGTH+" characters");
    }else if(email.length() > Constants.MAX_EMAIL_LENGTH) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "email", email.length(),
          "Email too long! Maximum "+Constants.MAX_EMAIL_LENGTH+" characters");
    } else if(!Pattern.matches(Constants.EMAIL_REGEX, email)) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "email", email, "Wrong email characters!");
    }
  }

  public static void validateAnswer(Answer answer) {
    if(answer.getPointsCount() > Constants.ANSWER_MAX_POINT_COUNT || answer.getPointsCount() < Constants.ANSWER_MIN_POINT_COUNT) {
      throw new EntityFieldValidationException(Answer.class.getSimpleName(), "pointsCount", answer.getPointsCount(), "Points must be in range (" + Constants.ANSWER_MIN_POINT_COUNT + ", " + Constants.ANSWER_MAX_POINT_COUNT + ")!");
    } else {
      if(!answer.getQuestion().isClosed()) {
        if(answer.getQuestion().getAnswers().size() > 1) {
          throw new EntityFieldValidationException(Question.class.getSimpleName(), "answers", "count: " + answer.getQuestion().getAnswers().size(), "Open questions must have only one answer!");
        } else if(!answer.isCorrect()) {
          throw new EntityFieldValidationException(Answer.class.getSimpleName(), "isCorrect", answer.isCorrect(), "Open questions must have only correct answer!");
        } else if(!answer.getPointsCount().equals(Constants.ANSWER_OPEN_POINTS)) {
          throw new EntityFieldValidationException(Answer.class.getSimpleName(), "pointsCount", answer.getPointsCount(), "Open question's answer must give 0 points!");
        }
      } else {
        if(answer.isCorrect() && answer.getPointsCount() <= 0) {
          throw new EntityFieldValidationException(Answer.class.getSimpleName(), "pointsCount", answer.getPointsCount(), "Correct answer must give more than 0 points!");
        }
        if(!answer.isCorrect() && answer.getPointsCount() > 0) {
          throw new EntityFieldValidationException(Answer.class.getSimpleName(), "pointsCount", answer.getPointsCount(), "Not correct answer must give 0 or less points!");
        }
      }
    }
  }
}