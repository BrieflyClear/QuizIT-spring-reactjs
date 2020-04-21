package com.wap.quizit.service;

import com.wap.quizit.model.Role;
import com.wap.quizit.model.User;
import com.wap.quizit.repository.RoleRepository;
import com.wap.quizit.repository.UserRepository;
import com.wap.quizit.service.dto.RegisterUserDTO;
import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.exception.UserNotExistsException;
import com.wap.quizit.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;

  public User getById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotExistsException(id));
  }

  public Optional<User> getByIdNoException(Long id) {
    return userRepository.findById(id);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public Optional<User> getByUsernameNoException(String username) {
    return userRepository.findByUsername(username);
  }

  public User getByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotExistsException(username));
  }

  public Optional<User> getByEmailNoException(String email) {
    return userRepository.findByEmail(email);
  }

  public User getByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException(email));
  }

  public User registerUser(RegisterUserDTO registerForm) {
    User user = new User();
    user.setId(Constants.DEFAULT_ID);
    user.setReportsIssued(new HashSet<>());
    user.setQuizzes(new HashSet<>());
    user.setComments(new HashSet<>());
    user.setRole(roleRepository.findById(Constants.ROLE_USER)
        .orElseThrow(() -> new EntityNotFoundException(Role.class, Constants.ROLE_USER)));
    user.setEmail(registerForm.getEmail());
    user.setPassword(registerForm.getPassword());
    user.setActivePremium(false);
    user.setUsername(registerForm.getUsername());
    if(user.getUsername().length() > 15) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "username", user.getUsername(), "Username too long! Maximum 15 characters");
    }
    if(user.getPassword().length() > 60) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "password", "---", "Password too long! Maximum 60 characters");
    }
    return userRepository.save(user);
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
