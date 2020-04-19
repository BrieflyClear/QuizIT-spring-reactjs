package com.wap.quizit.mapper;

import com.wap.quizit.dto.AnswerDTO;
import com.wap.quizit.mapper.decorator.AnswerMapperDecorator;
import com.wap.quizit.model.Answer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(AnswerMapperDecorator.class)
public interface AnswerMapper {

  AnswerDTO map(Answer entity);
  Answer map(AnswerDTO dto);
}
