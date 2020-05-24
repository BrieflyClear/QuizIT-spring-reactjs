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

  public Quiz getById(Long id, boolean includePrivate) {
    if(includePrivate) {
      return quizRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Quiz.class, id));
    } else {
      return quizRepository.findByIdAndIsPublic(id, true).orElseThrow(() -> new EntityNotFoundException(Quiz.class, id));
    }
  }

  public List<Quiz> getAll(boolean includePrivate) {
    if(includePrivate) {
      return quizRepository.findAll();
    } else {
      return quizRepository.findAllByIsPublic(true);
    }
  }

  public List<Quiz> getByCategoryId(Long categoryId, boolean includePrivate) {
    if(includePrivate) {
      return quizRepository.findByCategoriesCategoryId(categoryId);
    } else {
      return quizRepository.findByCategoriesCategoryIdAndIsPublic(categoryId, true);
    }
  }

  public List<Quiz> getByTitleFragment(String fragment, boolean includePrivate) {
    if(includePrivate) {
      return quizRepository.findByTitleContainingIgnoreCase(fragment);
    } else {
      return quizRepository.findByTitleContainingIgnoreCaseAndIsPublic(fragment, true);
    }
  }

  public Quiz save(Quiz quiz) {
    return quizRepository.save(quiz);
  }

  public void deleteById(Long id) {
    quizRepository.deleteById(id);
  }
}
