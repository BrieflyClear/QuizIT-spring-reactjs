package com.wap.quizit.service;

import com.wap.quizit.model.QuizCategory;
import com.wap.quizit.repository.QuizCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizCategoryService {
  
  private QuizCategoryRepository quizCategoryRepository;

  public Optional<QuizCategory> getById(Long id) {
    return quizCategoryRepository.findById(id);
  }

  public List<QuizCategory> getAll() {
    return quizCategoryRepository.findAll();
  }

  public QuizCategory save(QuizCategory quizCategory) {
    return quizCategoryRepository.save(quizCategory);
  }

  public void deleteById(Long id) {
    quizCategoryRepository.deleteById(id);
  }
}
