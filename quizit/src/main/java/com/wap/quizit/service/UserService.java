package com.wap.quizit.service;

import com.wap.quizit.model.User;
import com.wap.quizit.repository.UserRepository;
import com.wap.quizit.service.exception.UserNotExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

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

  public User save(User user) {
    return userRepository.save(user);
  }

  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
