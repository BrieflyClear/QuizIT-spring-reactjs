package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.AnswerDTO;
import com.wap.quizit.mapper.AnswerMapper;
import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Question;
import com.wap.quizit.service.AnswerService;
import com.wap.quizit.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

@AllArgsConstructor
public abstract class AnswerMapperDecorator implements AnswerMapper {

  @Qualifier("delegate")
  private AnswerMapper delegate;
  private AnswerService answerService;
  private QuestionService questionService;

  /**
   * Should throw error when the Question is null
   */
  @Override
  public Answer map(AnswerDTO dto) {
    var answer = delegate.map(dto);
    Question question = questionService.getById(dto.getQuestion()).orElse(null);
    answer.setQuestion(question);
    return answer;
  }
}
