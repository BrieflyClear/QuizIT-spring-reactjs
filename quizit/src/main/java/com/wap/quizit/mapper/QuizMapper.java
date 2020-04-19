package com.wap.quizit.mapper;

import com.wap.quizit.dto.QuizDTO;
import com.wap.quizit.mapper.decorator.QuizMapperDecorator;
import com.wap.quizit.model.Category;
import com.wap.quizit.model.Quiz;
import com.wap.quizit.model.QuizCategory;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(QuizMapperDecorator.class)
public interface QuizMapper {

  @Mapping(target = "categories", expression = "java(convert(entity.getCategories()))")
  QuizDTO map(Quiz entity);

  Quiz map(QuizDTO dto);

  default Set<Long> convert(Set<QuizCategory> categories) {
    return categories.stream().map(QuizCategory::getCategory).map(Category::getId).collect(Collectors.toSet());
  }
}
