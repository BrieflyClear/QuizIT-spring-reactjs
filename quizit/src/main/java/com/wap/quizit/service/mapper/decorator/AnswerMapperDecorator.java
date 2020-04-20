package com.wap.quizit.service.mapper.decorator;

import com.wap.quizit.service.dto.AnswerDTO;
import com.wap.quizit.service.mapper.AnswerMapper;
import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Question;
import com.wap.quizit.service.AnswerService;
import com.wap.quizit.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AnswerMapperDecorator implements AnswerMapper {

  @Autowired
  @Qualifier("delegate")
  private AnswerMapper delegate;
  @Autowired
  private AnswerService answerService;
  @Autowired
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
