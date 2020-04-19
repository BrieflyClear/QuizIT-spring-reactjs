package com.wap.quizit.mapper;

import com.wap.quizit.dto.QuizDTO;
import com.wap.quizit.mapper.decorator.QuizMapperDecorator;
import com.wap.quizit.model.*;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(QuizMapperDecorator.class)
public interface QuizMapper {

  @Mapping(target = "author", source = "entity.author.id")
  @Mapping(target = "isPublic", source = "public")
  @Mapping(target = "categories", expression = "java(convertCategories(entity.getCategories()))")
  @Mapping(target = "questions", expression = "java(convertQuestions(entity.getQuestions()))")
  @Mapping(target = "reportsIssued", expression = "java(convertReports(entity.getReportsIssued()))")
  QuizDTO map(Quiz entity);

  @Mapping(target = "author", ignore = true)
  @Mapping(target = "public", source = "isPublic")
  @Mapping(target = "categories", ignore = true)
  @Mapping(target = "questions", ignore = true)
  @Mapping(target = "reportsIssued", ignore = true)
  Quiz map(QuizDTO dto);

  default List<Long> convertCategories(Set<QuizCategory> list) {
    return list.stream().map(QuizCategory::getCategory).map(Category::getId).collect(Collectors.toList());
  }
  default List<Long> convertQuestions(Set<Question> list) {
    return list.stream().map(Question::getId).collect(Collectors.toList());
  }
  default List<Long> convertReports(Set<Report> list) {
    return list.stream().map(Report::getId).collect(Collectors.toList());
  }
}
