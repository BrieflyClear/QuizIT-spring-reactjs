package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.QuestionDTO;
import com.wap.quizit.mapper.QuestionMapper;
import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Comment;
import com.wap.quizit.model.Question;
import com.wap.quizit.service.AnswerService;
import com.wap.quizit.service.CommentService;
import com.wap.quizit.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public abstract class QuestionMapperDecorator implements QuestionMapper {

  @Qualifier("delegate")
  private QuestionMapper delegate;
  private QuizService quizService;
  private AnswerService answerService;
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
