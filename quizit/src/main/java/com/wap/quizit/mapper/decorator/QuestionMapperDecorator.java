package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.QuestionDTO;
import com.wap.quizit.mapper.QuestionMapper;
import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Comment;
import com.wap.quizit.model.Question;
import com.wap.quizit.service.AnswerService;
import com.wap.quizit.service.CommentService;
import com.wap.quizit.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

public abstract class QuestionMapperDecorator implements QuestionMapper {

  @Autowired
  @Qualifier("delegate")
  private QuestionMapper delegate;
  @Autowired
  private QuizService quizService;
  @Autowired
  private AnswerService answerService;
  @Autowired
  private CommentService commentService;

  /**
   * Should throw error instead of null
   */
  @Override
  public Question map(QuestionDTO dto) {
    var question = delegate.map(dto);
    question.setQuiz(quizService.getById(dto.getQuiz()).orElse(null));
    Set<Answer> answers = new HashSet<>();
    dto.getAnswers().forEach(id -> answerService.getById(id).map(answers::add));
    Set<Comment> comments = new HashSet<>();
    dto.getComments().forEach(id -> commentService.getById(id).map(comments::add));
    question.setComments(comments);
    question.setAnswers(answers);
    return question;
  }
}
