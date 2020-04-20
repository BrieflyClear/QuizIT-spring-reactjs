package com.wap.quizit.service;

import com.wap.quizit.model.User;
import com.wap.quizit.repository.RoleRepository;
import com.wap.quizit.repository.UserRepository;
import com.wap.quizit.service.dto.RegisterUserDTO;
import com.wap.quizit.service.exception.RoleNotFoundException;
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

  public Optional<User> getById(Long id) {
    return userRepository.findById(id);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> getByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User registerUser(RegisterUserDTO registerForm) {
    User user = new User();
    user.setId(Constants.DEFAULT_ID);
    user.setReportsIssued(new HashSet<>());
    user.setQuizzes(new HashSet<>());
    user.setComments(new HashSet<>());
    user.setRole(roleRepository.findById(Constants.ROLE_USER).orElseThrow(RoleNotFoundException::new));
    user.setEmail(registerForm.getEmail());
    user.setPassword(registerForm.getPassword());
    user.setUsername(registerForm.getUsername());
    userRepository.save(user);
    return user;
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
