package com.wap.quizit.service;

import com.wap.quizit.model.QuizCategory;
import com.wap.quizit.repository.QuizCategoryRepository;
import com.wap.quizit.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizCategoryService {
  
  private QuizCategoryRepository quizCategoryRepository;

  public Optional<QuizCategory> getByIdNoException(Long id) {
    return quizCategoryRepository.findById(id);
  }

  public QuizCategory getById(Long id) {
    return quizCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(QuizCategory.class, id));
  }

  public List<QuizCategory> getAll() {
    return quizCategoryRepository.findAll();
  }

  public QuizCategory getByQuizAndCategoryId(Long quizId, Long categoryId) {
    return quizCategoryRepository.findByQuizIdAndCategoryId(quizId, categoryId)
        .orElseThrow(() -> new EntityNotFoundException(QuizCategory.class));
  }

  public Optional<QuizCategory> getByQuizAndCategoryIdNoException(Long quizId, Long categoryId) {
    return quizCategoryRepository.findByQuizIdAndCategoryId(quizId, categoryId);
  }

  public QuizCategory save(QuizCategory quizCategory) {
    return quizCategoryRepository.save(quizCategory);
  }

  public void deleteById(Long id) {
    quizCategoryRepository.deleteById(id);
  }
}
