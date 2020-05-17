package com.wap.quizit.util;

import com.wap.quizit.model.*;
import com.wap.quizit.service.exception.AttemptAnswerNotMatchingQuestion;
import com.wap.quizit.service.exception.AttemptAnswerNotMatchingQuiz;
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

  public static void validateRole(Role role) {
    if(!Pattern.matches(Constants.ROLE_REGEX, role.getName())) {
      throw new EntityFieldValidationException(
          Role.class.getSimpleName(), "name", role.getName(), "Wrong name characters! " +
          "Length must be in range 3-15. Only alphanumeric, '_', '-' and spaces characters are allowed.");
    }
  }

  public static void validateReport(Report report) {
    if(!Pattern.matches(Constants.REPORT_TITLE_REGEX, report.getTitle())) {
      throw new EntityFieldValidationException(
          Report.class.getSimpleName(), "title", report.getTitle(), "Wrong title characters! " +
          "Length must be in range 3-50. Only alphanumeric, '_', '-' and spaces characters are allowed.");
    }
    if(!Pattern.matches(Constants.REPORT_DESCRIPTION_REGEX, report.getDescription())) {
      throw new EntityFieldValidationException(
          Report.class.getSimpleName(), "description", report.getDescription(), "Wrong description characters! " +
          "Length must be in range 3-200. Only alphanumeric, '_', '-' and spaces characters are allowed.");
    }
    if(!Pattern.matches(Constants.REPORT_STATUS_REGEX, report.getStatus())) {
      throw new EntityFieldValidationException(
          Report.class.getSimpleName(), "status", report.getStatus(), "Wrong status characters! " +
          "Length must be in range 3-20. Only alphanumeric, '_', '-' and spaces characters are allowed.");
    }
  }

  public static void validateCategory(Category category) {
    if(!Pattern.matches(Constants.CATEGORY_REGEX, category.getName())) {
      throw new EntityFieldValidationException(Category.class.getSimpleName(), "name", category.getName(),
          "Wrong characters! " +
              "Length must be in range 3-40. Only alphanumeric, '_', '-' and spaces characters are allowed.");
    }
  }

  public static void validateComment(Comment comment) {
    if(!Pattern.matches(Constants.COMMENT_REGEX, comment.getContents())) {
      throw new EntityFieldValidationException(
          Comment.class.getSimpleName(), "contents", comment.getContents(),
          "Wrong characters! " +
              "Length must be in range 3-200. Only alphanumeric, '_', '-' and spaces characters are allowed.");
    }
  }

  public static void validateQuiz(Quiz quiz) {
    if(!Pattern.matches(Constants.QUIZ_REGEX, quiz.getTitle())) {
      throw new EntityFieldValidationException(
          Quiz.class.getSimpleName(), "title", quiz.getTitle(), "Wrong characters! " +
          "Length must be in range 3-50. Only alphanumeric, '_', '-' and spaces characters are allowed.");
    }
  }

  public static void validateQuestion(Question question) {
    if(!Pattern.matches(Constants.QUESTION_REGEX, question.getContents())) {
      throw new EntityFieldValidationException(
          Question.class.getSimpleName(), "contents", question.getContents(), "Question is too short or too long! " +
          "Length must be in range 3-1000 characters.");
    }
    validateQuestionAnswers(question);
    if(!question.getUserQuizAttemptsAnswers().isEmpty()
      && !question.getUserQuizAttemptsAnswers().stream().map(UserQuizAttemptAnswer::getQuestion)
        .mapToLong(Question::getId).allMatch(a -> a == question.getId())) {
        throw new EntityFieldValidationException(
            Question.class.getSimpleName(), "userQuizAttemptsAnswers", "---",
            "UserQuizAttemptsAnswers do not refer to the question!");
    }
  }

  public static void validateQuestionAnswers(Question question) {
    if(!question.getAnswers().isEmpty()) {
      question.getAnswers().forEach(DataValidator::validateAnswer);
      if(!question.isClosed()) {
        validateOpenQuestion(question);
      } else { /** closed question */
         if(question.getAnswers().stream().noneMatch(Answer::isCorrect)) {
           throw new EntityFieldValidationException(Question.class.getSimpleName(), "answers", "---",
               "At least one answer must be correct!");
         }
        /** multiple choice questions don't have any special requirements */
        if(!question.isMultipleChoice()) {
          if(question.getAnswers().stream().filter(Answer::isCorrect).count() > 1) {
            throw new EntityFieldValidationException(Question.class.getSimpleName(), "answers", "---",
                "Non multiple-choice question cannot have multiple correct answers!");
          }
        }
      }
    } else {
      throw new EntityFieldValidationException(Question.class.getSimpleName(), "answers",
          "count: " + question.getAnswers().size(), "Question must have answers!");
    }
  }

  public static void validateOpenQuestion(Question question) {
    if(question.isMultipleChoice()) {
      throw new EntityFieldValidationException(Question.class.getSimpleName(), "isMultipleChoice",
          question.isMultipleChoice(), "Question cannot be open and multiple choice at once!");
    } else if(question.getAnswers().size() > 1) {
      throw new EntityFieldValidationException(Question.class.getSimpleName(), "answers",
          "count: " + question.getAnswers().size(),
          "Question that is not a closed question must have only one answer!");
    } else if(question.getAnswers().size() == 1) {
      if(question.getAnswers().stream().noneMatch(Answer::isCorrect)) {
        throw new EntityFieldValidationException(Question.class.getSimpleName(), "answers", "correct: false",
            "Question that is not a closed question must have only one answer that is correct!");
      } else if(question.getAnswers().stream().noneMatch(a -> a.getPointsCount()
          .equals(Constants.OPEN_QUESTION_ANSWER_POINTS))) {
        throw new EntityFieldValidationException(Question.class.getSimpleName(), "answers",
            "points count: " + question.getAnswers().stream().mapToInt(Answer::getPointsCount).sum(),
            "Question that is not a closed question must have only "
                + Constants.OPEN_QUESTION_ANSWER_POINTS + " points!");
      }
    }
  }

  public static void validateAnswer(Answer answer) {
    if(!Pattern.matches(Constants.ANSWER_REGEX, answer.getContents())) {
      throw new EntityFieldValidationException(
          Answer.class.getSimpleName(), "contents", answer.getContents(), "Answer is too short or too long! " +
          "Length must be in range 3-4000 characters.");
    }
    validateAnswerPoints(answer);
    if(!answer.getUserQuizAttemptAnswers().isEmpty()
        && !answer.getUserQuizAttemptAnswers().stream().map(UserQuizAttemptAnswer::getAnswerGiven)
        .mapToLong(Answer::getId).allMatch(a -> a == answer.getId())) {
      throw new EntityFieldValidationException(
          Question.class.getSimpleName(), "userQuizAttemptsAnswers", "---",
          "UserQuizAttemptsAnswers do not refer to the answer!");
    }
  }

  public static void validateAnswerPoints(Answer answer) {
    if(answer.getPointsCount() > Constants.ANSWER_MAX_POINT_COUNT
        || answer.getPointsCount() < Constants.ANSWER_MIN_POINT_COUNT) {
      throw new EntityFieldValidationException(Answer.class.getSimpleName(), "pointsCount", answer.getPointsCount(),
          "Points must be in range (" + Constants.ANSWER_MIN_POINT_COUNT + ", "
              + Constants.ANSWER_MAX_POINT_COUNT + ")!");
    } else {
      if(answer.getQuestion().isClosed()) {
        if(answer.isCorrect() && answer.getPointsCount() <= 0) {
          throw new EntityFieldValidationException(Answer.class.getSimpleName(), "pointsCount",
              answer.getPointsCount(), "Correct answer must give more than 0 points!");
        }
        if(!answer.isCorrect() && answer.getPointsCount() > 0) {
          throw new EntityFieldValidationException(Answer.class.getSimpleName(), "pointsCount",
              answer.getPointsCount(), "Wrong answer must give 0 or less points!");
        }
      }
    }
  }

  public static void validateUserQuizAttempt(UserQuizAttempt attempt) {
    attempt.getAttemptAnswers().forEach(DataValidator::validateUserQuizAttemptAnswer);
  }

  public static void validateUserQuizAttemptAnswer(UserQuizAttemptAnswer attemptAnswer) {
    if(!attemptAnswer.getAttempt().getQuiz().getId().equals(attemptAnswer.getQuestion().getQuiz().getId())) {
      throw new AttemptAnswerNotMatchingQuiz(attemptAnswer.getId(), attemptAnswer.getAttempt().getId());
    }
    if(attemptAnswer.getQuestion().getAnswers()
        .stream().noneMatch(it -> it.getId().equals(attemptAnswer.getAnswerGiven().getId()))) {
      throw new AttemptAnswerNotMatchingQuestion(attemptAnswer.getId(), attemptAnswer.getQuestion().getId());
    }
  }
}
