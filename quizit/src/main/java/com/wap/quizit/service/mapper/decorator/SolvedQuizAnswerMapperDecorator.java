package com.wap.quizit.service.mapper.decorator;

import com.wap.quizit.model.SolvedQuizAnswer;
import com.wap.quizit.service.*;
import com.wap.quizit.service.dto.SolvedQuizAnswerDTO;
import com.wap.quizit.service.mapper.SolvedQuizAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class SolvedQuizAnswerMapperDecorator implements SolvedQuizAnswerMapper {

  @Autowired
  @Qualifier("delegate")
  private SolvedQuizAnswerMapper delegate;
  @Autowired
  private AnswerService answerService;
  @Autowired
  private QuestionService questionService;
  @Autowired
  private UserService userService;

  @Override
  public SolvedQuizAnswer map(SolvedQuizAnswerDTO dto) {
    var userAnswer = delegate.map(dto);
    userAnswer.setQuestion(questionService.getById(dto.getQuestion()).orElse(null));
    userAnswer.setUser(userService.getById(dto.getUser()).orElse(null));
    userAnswer.setAnswerGiven(answerService.getById(dto.getAnswerGiven()).orElse(null));
    return userAnswer;
  }
}
