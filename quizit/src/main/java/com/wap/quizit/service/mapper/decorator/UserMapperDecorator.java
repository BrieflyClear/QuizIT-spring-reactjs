package com.wap.quizit.service.mapper.decorator;

import com.wap.quizit.model.*;
import com.wap.quizit.service.*;
import com.wap.quizit.service.dto.RegisterUserDTO;
import com.wap.quizit.service.dto.UserDTO;
import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.UserMapper;
import com.wap.quizit.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

public abstract class UserMapperDecorator implements UserMapper {

  @Autowired
  @Qualifier("delegate")
  private UserMapper delegate;
  @Autowired
  private QuizService quizService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ReportService reportService;
  @Autowired
  private UserService userService;

  @Override
  public User map(UserDTO dto) {
    var user = delegate.map(dto);
    user.setRole(roleService.getById(dto.getRole()));
    Set<Quiz> quizzes = new HashSet<>();
    dto.getQuizzes().forEach(id -> quizzes.add(quizService.getById(id)));
    Set<Comment> comments = new HashSet<>();
    dto.getComments().forEach(id -> comments.add(commentService.getById(id)));
    Set<Report> reports = new HashSet<>();
    dto.getReportsIssued().forEach(id -> reports.add(reportService.getById(id)));
    user.setComments(comments);
    user.setQuizzes(quizzes);
    user.setReportsIssued(reports);
    return user;
  }

  @Override
  public User map(RegisterUserDTO registerForm) {
    User user = new User();
    user.setId(Constants.DEFAULT_ID);
    user.setReportsIssued(new HashSet<>());
    user.setQuizzes(new HashSet<>());
    user.setComments(new HashSet<>());
    user.setQuizAttempts(new HashSet<>());
    user.setRole(roleService.getById(Constants.ROLE_USER));
    user.setEmail(registerForm.getEmail());
    user.setPassword(registerForm.getPassword());
    user.setActivePremium(false);
    user.setUsername(registerForm.getUsername());
    return user;
  }
}
