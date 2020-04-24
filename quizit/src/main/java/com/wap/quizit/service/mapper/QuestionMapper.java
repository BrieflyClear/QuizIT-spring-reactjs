package com.wap.quizit.service.mapper;

import com.wap.quizit.service.dto.CreateQuestionDTO;
import com.wap.quizit.service.dto.QuestionDTO;
import com.wap.quizit.service.dto.QuizQuestionFileDTO;
import com.wap.quizit.service.mapper.decorator.QuestionMapperDecorator;
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
  @Mapping(target = "isClosed", source = "closed")
  @Mapping(target = "isMultipleChoice", source = "multipleChoice")
  @Mapping(target = "answers", expression = "java(convertAnswers(entity.getAnswers()))")
  @Mapping(target = "comments", expression = "java(convertComments(entity.getComments()))")
  QuestionDTO map(Question entity);

  @Mapping(target = "quiz", ignore = true)
  @Mapping(target = "answers", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "userQuizAttemptsAnswers", ignore = true)
  @Mapping(target = "closed", source = "dto.isClosed")
  @Mapping(target = "multipleChoice", source = "dto.isMultipleChoice")
  Question map(QuestionDTO dto);

  @Mapping(target = "quiz", ignore = true)
  @Mapping(target = "answers", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "userQuizAttemptsAnswers", ignore = true)
  @Mapping(target = "closed", source = "dto.isClosed")
  @Mapping(target = "multipleChoice", source = "dto.isMultipleChoice")
  Question map(CreateQuestionDTO dto);

  @Mapping(target = "quiz", ignore = true)
  @Mapping(target = "answers", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "userQuizAttemptsAnswers", ignore = true)
  @Mapping(target = "closed", source = "dto.isClosed")
  @Mapping(target = "multipleChoice", source = "dto.isMultipleChoice")
  Question mapFromFileFormat(QuizQuestionFileDTO dto);

  @Mapping(target = "answers", ignore = true)
  @Mapping(target = "isClosed", source = "closed")
  @Mapping(target = "isMultipleChoice", source = "multipleChoice")
  QuizQuestionFileDTO mapToFileFormat(Question entity);

  default List<Long> convertAnswers(Set<Answer> list) {
    return list.stream().map(Answer::getId).collect(Collectors.toList());
  }
  default List<Long> convertComments(Set<Comment> list) {
    return list.stream().map(Comment::getId).collect(Collectors.toList());
  }
}
