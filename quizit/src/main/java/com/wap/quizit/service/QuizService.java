package com.wap.quizit.service;

import com.wap.quizit.model.Quiz;
import com.wap.quizit.repository.QuizRepository;
import com.wap.quizit.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {
  
  private QuizRepository quizRepository;

  public Optional<Quiz> getByIdNoException(Long id) {
    return quizRepository.findById(id);
  }

  public Quiz getById(Long id) {
    return quizRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Quiz.class, id));
  }

  public List<Quiz> getAll() {
    return quizRepository.findAll();
  }

  public List<Quiz> getByCategoryId(Long categoryId) {
    return quizRepository.findByCategoriesId(categoryId);
  }

  public List<Quiz> getByTitleFragment(String fragment) {
    return quizRepository.findByTitleContainingIgnoreCase(fragment);
  }

  public Quiz save(Quiz quiz) {
    return quizRepository.save(quiz);
  }

  public void deleteById(Long id) {
    quizRepository.deleteById(id);
  }
}
