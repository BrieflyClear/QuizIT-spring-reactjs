package com.wap.quizit.service;

import com.wap.quizit.model.Answer;
import com.wap.quizit.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerService {
  
  private AnswerRepository answerRepository;

  public Optional<Answer> getById(Long id) {
    return answerRepository.findById(id);
  }

  public List<Answer> getAll() {
    return answerRepository.findAll();
  }

  public Answer save(Answer answer) {
    return answerRepository.save(answer);
  }

  public void deleteById(Long id) {
    answerRepository.deleteById(id);
  }
}
