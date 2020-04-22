package com.wap.quizit.service.mapper;

import com.wap.quizit.service.dto.AnswerDTO;
import com.wap.quizit.service.dto.CreateAnswerDTO;
import com.wap.quizit.service.mapper.decorator.AnswerMapperDecorator;
import com.wap.quizit.model.Answer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(AnswerMapperDecorator.class)
public interface AnswerMapper {

  @Mapping(target = "question", source = "entity.question.id")
  @Mapping(target = "isCorrect", source = "correct")
  @Mapping(target = "pointsCount", source = "entity.pointsCount")
  AnswerDTO map(Answer entity);

  @Mapping(target = "question", ignore = true)
  @Mapping(target = "correct", source = "isCorrect")
  @Mapping(target = "pointsCount", source = "dto.pointsCount")
  @Mapping(target = "userQuizAttemptAnswers", ignore = true)
  Answer map(AnswerDTO dto);

  @Mapping(target = "question", ignore = true)
  @Mapping(target = "correct", source = "isCorrect")
  @Mapping(target = "pointsCount", source = "dto.pointsCount")
  @Mapping(target = "userQuizAttemptAnswers", ignore = true)
  Answer map(CreateAnswerDTO dto);
}
