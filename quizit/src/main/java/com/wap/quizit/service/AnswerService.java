package com.wap.quizit.service;

import com.wap.quizit.model.Answer;
import com.wap.quizit.repository.AnswerRepository;
import com.wap.quizit.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerService {
  
  private AnswerRepository answerRepository;

  public Answer getById(Long id) {
    return answerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Answer.class, id));
  }

  public Optional<Answer> getByIdNoException(Long id) {
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
