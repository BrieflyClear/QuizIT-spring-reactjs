package com.wap.quizit.mapper;

import com.wap.quizit.dto.AnswerDTO;
import com.wap.quizit.mapper.decorator.AnswerMapperDecorator;
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
  AnswerDTO map(Answer entity);

  @Mapping(target = "question", ignore = true)
  @Mapping(target = "correct", source = "correct")
  Answer map(AnswerDTO dto);
}
