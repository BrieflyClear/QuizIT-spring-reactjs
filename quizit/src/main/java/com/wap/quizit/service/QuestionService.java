package com.wap.quizit.service;

import com.wap.quizit.model.Question;
import com.wap.quizit.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {
  
  private QuestionRepository questionRepository;

  public Optional<Question> getById(Long id) {
    return questionRepository.findById(id);
  }

  public List<Question> getAll() {
    return questionRepository.findAll();
  }

  public Question save(Question question) {
    return questionRepository.save(question);
  }

  public void deleteById(Long id) {
    questionRepository.deleteById(id);
  }
}
