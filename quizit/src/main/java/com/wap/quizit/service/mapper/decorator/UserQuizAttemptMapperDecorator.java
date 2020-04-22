package com.wap.quizit.service.mapper.decorator;

import com.wap.quizit.model.Answer;
import com.wap.quizit.model.UserQuizAttempt;
import com.wap.quizit.model.UserQuizAttemptAnswer;
import com.wap.quizit.service.*;
import com.wap.quizit.service.dto.UserQuizAttemptAnswerDTO;
import com.wap.quizit.service.dto.UserQuizAttemptDTO;
import com.wap.quizit.service.dto.UserQuizQuestionSummary;
import com.wap.quizit.service.dto.UserQuizSummary;
import com.wap.quizit.service.mapper.UserQuizAttemptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public abstract class UserQuizAttemptMapperDecorator implements UserQuizAttemptMapper {

  @Autowired
  @Qualifier("delegate")
  private UserQuizAttemptMapper delegate;
  @Autowired
  private UserQuizAttemptService userAnswerService;
  @Autowired
  private QuizService quizService;
  @Autowired
  private UserService userService;
  @Autowired
  private AnswerService answerService;
  @Autowired
  private QuestionService questionService;

  @Override
  public UserQuizAttempt map(UserQuizAttemptDTO dto) {
    var userAttempt = delegate.map(dto);
    userAttempt.setQuiz(quizService.getById(dto.getQuiz()));
    userAttempt.setUser(userService.getById(dto.getUser()));
    Set<UserQuizAttemptAnswer> answers = new HashSet<>();
    dto.getAttemptAnswers().forEach(id -> answers.add(userAnswerService.getQuizAttemptAnswerById(id)));
    userAttempt.setAttemptAnswers(answers);
    return userAttempt;
  }

  @Override
  public UserQuizAttemptAnswer map(UserQuizAttemptAnswerDTO dto) {
    var userAnswer = delegate.map(dto);
    userAnswer.setAnswerGiven(answerService.getById(dto.getAnswerGiven()));
    userAnswer.setQuestion(questionService.getById(dto.getQuestion()));
    userAnswer.setAttempt(userAnswerService.getById(dto.getAttempt()));
    return userAnswer;
  }

  @Override
  public UserQuizSummary mapToSummary(UserQuizAttempt entity) {
    var summary = UserQuizSummary.builder();
    summary.attemptId(entity.getId());
    summary.user(entity.getUser().getId());
    summary.quiz(entity.getQuiz().getId());
    summary.attemptTime(entity.getAttemptTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    List<UserQuizQuestionSummary> questionSummaries = new ArrayList<>();
    entity.getQuiz().getQuestions().forEach(question -> {
      var questionSummary = UserQuizQuestionSummary.builder();
      questionSummary.question(question.getId());
      int maxPoints = question.getAnswers().stream()
          .map(Answer::getPointsCount).mapToInt(Integer::intValue).sum();
      questionSummary.maxPointsCount(maxPoints);
      List<Answer> list = entity.getAttemptAnswers().stream()
          .filter(it -> it.getQuestion().getId().equals(question.getId())).map(UserQuizAttemptAnswer::getAnswerGiven)
          .collect(Collectors.toList());
      if(list.size() > 1) {
        int pointsGained = list.stream().map(Answer::getPointsCount).mapToInt(Integer::intValue).sum();
        questionSummary.pointsGained(pointsGained);
        List<Long> ids = list.stream().map(Answer::getId).collect(Collectors.toList());
        questionSummary.answersGiven(ids);
      } else {
        questionSummary.pointsGained(maxPoints);
        questionSummary.answersGiven(Collections.emptyList());
      }
      questionSummaries.add(questionSummary.build());
    });
    summary.maxPointsCount(questionSummaries.stream()
        .map(UserQuizQuestionSummary::getMaxPointsCount).mapToInt(Integer::intValue).sum());
    summary.pointsGained(questionSummaries.stream()
        .map(UserQuizQuestionSummary::getPointsGained).mapToInt(Integer::intValue).sum());
    summary.questions(questionSummaries);
    return summary.build();
  }
}
