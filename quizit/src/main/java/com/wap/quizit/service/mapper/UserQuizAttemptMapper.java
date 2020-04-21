package com.wap.quizit.service.mapper;

import com.wap.quizit.model.UserQuizAttempt;
import com.wap.quizit.model.UserQuizAttemptAnswer;
import com.wap.quizit.service.dto.UserQuizAttemptAnswerDTO;
import com.wap.quizit.service.dto.UserQuizAttemptDTO;
import com.wap.quizit.service.dto.UserQuizQuestionSummary;
import com.wap.quizit.service.dto.UserQuizSummary;
import com.wap.quizit.service.mapper.decorator.UserQuizAttemptMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(UserQuizAttemptMapperDecorator.class)
public interface UserQuizAttemptMapper {

  @Mapping(target = "quiz", source = "entity.quiz.id")
  @Mapping(target = "user", source = "entity.user.id")
  @Mapping(target = "attemptTime", source = "entity.attemptTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "attemptAnswers", expression = "java(convert(entity.getAttemptAnswers()))")
  UserQuizAttemptDTO map(UserQuizAttempt entity);

  @Mapping(target = "quiz", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "attemptAnswers", ignore = true)
  @Mapping(target = "attemptTime", source = "dto.attemptTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  UserQuizAttempt map(UserQuizAttemptDTO dto);


  @Mapping(target = "question", source = "entity.question.id")
  @Mapping(target = "attempt", source = "entity.attempt.id")
  @Mapping(target = "answerGiven", source = "entity.answerGiven.id")
  UserQuizAttemptAnswerDTO map(UserQuizAttemptAnswer entity);

  @Mapping(target = "question", ignore = true)
  @Mapping(target = "answerGiven", ignore = true)
  @Mapping(target = "attempt", ignore = true)
  UserQuizAttemptAnswer map(UserQuizAttemptAnswerDTO dto);


  UserQuizSummary mapToSummary(UserQuizAttempt entity);


  default List<Long> convert(Set<UserQuizAttemptAnswer> list) {
    return list.stream().map(UserQuizAttemptAnswer::getId).collect(Collectors.toList());
  }
}
