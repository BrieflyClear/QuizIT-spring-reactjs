package com.wap.quizit.service.mapper.decorator;

import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Comment;
import com.wap.quizit.model.Question;
import com.wap.quizit.service.AnswerService;
import com.wap.quizit.service.CommentService;
import com.wap.quizit.service.QuestionService;
import com.wap.quizit.service.QuizService;
import com.wap.quizit.service.dto.CreateQuestionDTO;
import com.wap.quizit.service.dto.QuestionDTO;
import com.wap.quizit.service.dto.QuizQuestionFileDTO;
import com.wap.quizit.service.mapper.AnswerMapper;
import com.wap.quizit.service.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
  @Autowired
  private QuestionService questionService;
  @Autowired
  private AnswerMapper answerMapper;

  @Override
  public Question map(QuestionDTO dto) {
    var question = delegate.map(dto);
    question.setQuiz(quizService.getById(dto.getQuiz(), true));
    Set<Answer> answers = new HashSet<>();
    dto.getAnswers().forEach(id -> answers.add(answerService.getById(id)));
    Set<Comment> comments = new HashSet<>();
    dto.getComments().forEach(id -> comments.add(commentService.getById(id)));
    question.setComments(comments);
    question.setAnswers(answers);
    question.setUserQuizAttemptsAnswers(
        questionService.getByIdNoException(dto.getId())
            .map(Question::getUserQuizAttemptsAnswers)
            .orElse(new HashSet<>()));
    return question;
  }

  @Override
  public Question map(CreateQuestionDTO dto) {
    var question = delegate.map(dto);
    question.setQuiz(quizService.getById(dto.getQuiz(), true));
    Set<Answer> answers = new HashSet<>();
    dto.getAnswers().forEach(answer -> answers.add(answerMapper.map(answer)));
    answers.forEach(it -> it.setQuestion(question));
    question.setAnswers(answers);
    question.setComments(new HashSet<>());
    question.setUserQuizAttemptsAnswers(new HashSet<>());
    return question;
  }

  @Override
  public Question mapFromFileFormat(QuizQuestionFileDTO dto) {
    var question = delegate.mapFromFileFormat(dto);
    Set<Answer> answers = new HashSet<>();
    dto.getAnswers().forEach(answer -> answers.add(answerMapper.map(answer)));
    answers.forEach(it -> it.setQuestion(question));
    question.setAnswers(answers);
    question.setComments(new HashSet<>());
    question.setUserQuizAttemptsAnswers(new HashSet<>());
    return question;
  }

  @Override
  public QuizQuestionFileDTO mapToFileFormat(Question entity) {
    var question = delegate.mapToFileFormat(entity);
    question.setAnswers(entity.getAnswers().stream().map(answerMapper::mapToFileFormat).collect(Collectors.toList()));
    return question;
  }
}
