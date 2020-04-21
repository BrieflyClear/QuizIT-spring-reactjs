package com.wap.quizit.service.mapper.decorator;

import com.wap.quizit.service.dto.QuestionDTO;
import com.wap.quizit.service.mapper.QuestionMapper;
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

  @Override
  public Question map(QuestionDTO dto) {
    var question = delegate.map(dto);
    question.setQuiz(quizService.getById(dto.getQuiz()));
    Set<Answer> answers = new HashSet<>();
    dto.getAnswers().forEach(id -> answers.add(answerService.getById(id)));
    Set<Comment> comments = new HashSet<>();
    dto.getComments().forEach(id -> comments.add(commentService.getById(id)));
    question.setComments(comments);
    question.setAnswers(answers);
    return question;
  }
}
