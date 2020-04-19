package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.RegisterUserDTO;
import com.wap.quizit.dto.UserDTO;
import com.wap.quizit.mapper.UserMapper;
import com.wap.quizit.model.Comment;
import com.wap.quizit.model.Quiz;
import com.wap.quizit.model.Report;
import com.wap.quizit.model.User;
import com.wap.quizit.service.*;
import com.wap.quizit.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public abstract class UserMapperDecorator implements UserMapper {

  @Qualifier("delegate")
  private UserMapper delegate;
  private QuizService quizService;
  private RoleService roleService;
  private CommentService commentService;
  private ReportService reportService;
  private UserService userService;

  /**
   * Should throw error instead of null
   */
  @Override
  public User map(UserDTO dto) {
    var user = delegate.map(dto);
    user.setRole(roleService.getById(dto.getRole()).orElse(null));
    Set<Quiz> quizzes = new HashSet<>();
    dto.getQuizzes().forEach(id -> quizService.getById(id).map(quizzes::add));
    Set<Comment> comments = new HashSet<>();
    dto.getComments().forEach(id -> commentService.getById(id).map(comments::add));
    Set<Report> reports = new HashSet<>();
    dto.getReportsIssued().forEach(id -> reportService.getById(id).map(reports::add));
    user.setComments(comments);
    user.setQuizzes(quizzes);
    user.setReportsIssued(reports);
    return user;
  }

  @Override
  public User map(RegisterUserDTO registerForm) {
    if(userService.getByUsername(registerForm.getUsername()).isEmpty()
        && userService.getByEmail(registerForm.getEmail()).isEmpty()) {
      User user = new User();
      user.setId(Constants.DEFAULT_ID);
      user.setReportsIssued(new HashSet<>());
      user.setQuizzes(new HashSet<>());
      user.setComments(new HashSet<>());
      user.setRole(roleService.getById(Constants.ROLE_USER).orElse(null));
      user.setEmail(registerForm.getEmail());
      user.setPassword(registerForm.getPassword());
      user.setUsername(registerForm.getUsername());
      return user;
    }
    return null;
  }
}
