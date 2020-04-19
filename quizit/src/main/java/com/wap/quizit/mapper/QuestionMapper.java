package com.wap.quizit.mapper;

import com.wap.quizit.dto.QuestionDTO;
import com.wap.quizit.mapper.decorator.QuestionMapperDecorator;
import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Comment;
import com.wap.quizit.model.Question;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(QuestionMapperDecorator.class)
public interface QuestionMapper {

  @Mapping(target = "quiz", source = "entity.quiz.id")
  @Mapping(target = "answers", expression = "java(convertAnswers(entity.getAnswers()))")
  @Mapping(target = "comments", expression = "java(convertComments(entity.getComments()))")
  QuestionDTO map(Question entity);

  @Mapping(target = "quiz", ignore = true)
  @Mapping(target = "answers", ignore = true)
  @Mapping(target = "comments", ignore = true)
  Question map(QuestionDTO dto);

  default List<Long> convertAnswers(Set<Answer> list) {
    return list.stream().map(Answer::getId).collect(Collectors.toList());
  }
  default List<Long> convertComments(Set<Comment> list) {
    return list.stream().map(Comment::getId).collect(Collectors.toList());
  }
}
