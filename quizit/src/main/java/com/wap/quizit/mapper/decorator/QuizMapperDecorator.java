package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.QuizDTO;
import com.wap.quizit.mapper.QuizMapper;
import com.wap.quizit.model.Question;
import com.wap.quizit.model.Quiz;
import com.wap.quizit.model.QuizCategory;
import com.wap.quizit.model.Report;
import com.wap.quizit.service.*;
import com.wap.quizit.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

public abstract class QuizMapperDecorator implements QuizMapper {

  @Autowired
  @Qualifier("delegate")
  private QuizMapper delegate;
  @Autowired
  private UserService userService;
  @Autowired
  private QuizCategoryService quizCategoryService;
  @Autowired
  private QuizService quizService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private QuestionService questionService;
  @Autowired
  private ReportService reportService;

  /**
   * Should throw error instead of null
   */
  @Override
  public Quiz map(QuizDTO dto) {
    var quiz = delegate.map(dto);
    var savedQuiz = quizService.getById(dto.getId());
    quiz.setAuthor(userService.getById(dto.getAuthor()).orElse(null));
    Set<QuizCategory> categories = new HashSet<>();
    dto.getCategories().forEach(id -> {
      boolean added = false;
      if(savedQuiz.isPresent()) {
        added = quizCategoryService.getByQuizAndCategoryId(dto.getId(), id).map(categories::add).orElse(false);
      }
      if(!added) {
        categoryService.getById(id).map(category -> categories.add(new QuizCategory(Constants.DEFAULT_ID, quiz, category)));
      }
    });
    quiz.setCategories(categories);
    Set<Question> questions = new HashSet<>();
    dto.getQuestions().forEach(id -> questionService.getById(id).map(questions::add));
    quiz.setQuestions(questions);
    Set<Report> reports = new HashSet<>();
    dto.getReportsIssued().forEach(id -> reportService.getById(id).map(reports::add));
    quiz.setReportsIssued(reports);
    return quiz;
  }
}
