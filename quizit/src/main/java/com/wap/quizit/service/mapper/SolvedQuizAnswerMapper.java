package com.wap.quizit.service.mapper;

import com.wap.quizit.model.SolvedQuizAnswer;
import com.wap.quizit.service.dto.SolvedQuizAnswerDTO;
import com.wap.quizit.service.mapper.decorator.SolvedQuizAnswerMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SolvedQuizAnswerMapperDecorator.class)
public interface SolvedQuizAnswerMapper {

  @Mapping(target = "question", source = "entity.question.id")
  @Mapping(target = "user", source = "entity.user.id")
  @Mapping(target = "answerGiven", source = "entity.answerGiven.id")
  SolvedQuizAnswerDTO map(SolvedQuizAnswer entity);

  @Mapping(target = "question", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "answerGiven", ignore = true)
  SolvedQuizAnswer map(SolvedQuizAnswerDTO dto);
}
