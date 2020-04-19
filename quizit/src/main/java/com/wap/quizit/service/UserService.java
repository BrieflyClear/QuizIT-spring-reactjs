package com.wap.quizit.service;

import com.wap.quizit.model.User;
import com.wap.quizit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

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

  public User save(User user) {
    return userRepository.save(user);
  }

  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
