package com.wap.quizit.mapper;

import com.wap.quizit.dto.QuestionDTO;
import com.wap.quizit.mapper.decorator.QuestionMapperDecorator;
import com.wap.quizit.model.Question;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(QuestionMapperDecorator.class)
public interface QuestionMapper {

  QuestionDTO map(Question entity);
  Question map(QuestionDTO dto);
}
