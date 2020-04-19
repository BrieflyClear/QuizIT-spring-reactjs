package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.CommentDTO;
import com.wap.quizit.mapper.CommentMapper;
import com.wap.quizit.model.Comment;
import com.wap.quizit.service.QuestionService;
import com.wap.quizit.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

@AllArgsConstructor
public abstract class CommentMapperDecorator implements CommentMapper {

  @Qualifier("delegate")
  private CommentMapper delegate;
  private UserService userService;
  private QuestionService questionService;

  /**
   * Should throw error when the Question or User is null
   */
  @Override
  public Comment map(CommentDTO dto) {
    var comment = delegate.map(dto);
    comment.setAuthor(userService.getById(dto.getAuthor()).orElse(null));
    comment.setQuestion(questionService.getById(dto.getQuestion()).orElse(null));
    return comment;
  }
}
