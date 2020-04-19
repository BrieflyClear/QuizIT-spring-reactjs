package com.wap.quizit.mapper;

import com.wap.quizit.dto.CommentDTO;
import com.wap.quizit.mapper.decorator.CommentMapperDecorator;
import com.wap.quizit.model.Comment;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(CommentMapperDecorator.class)
public interface CommentMapper {

  @Mapping(target="issuedTime", source = "issuedTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  CommentDTO map(Comment entity);

  @Mapping(target="issuedTime", source = "issuedTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  Comment map(CommentDTO dto);
}
