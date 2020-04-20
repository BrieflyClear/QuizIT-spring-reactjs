package com.wap.quizit.service;

import com.wap.quizit.model.Quiz;
import com.wap.quizit.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {
  
  private QuizRepository quizRepository;

  public Optional<Quiz> getById(Long id) {
    return quizRepository.findById(id);
  }

  public List<Quiz> getAll() {
    return quizRepository.findAll();
  }

  public Quiz save(Quiz quiz) {
    return quizRepository.save(quiz);
  }

  public void deleteById(Long id) {
    quizRepository.deleteById(id);
  }
}
