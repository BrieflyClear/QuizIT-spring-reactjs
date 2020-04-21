package com.wap.quizit.service;

import com.wap.quizit.model.UserQuizAttempt;
import com.wap.quizit.repository.SolvedQuizAnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SolvedQuizAnswerService {

  private SolvedQuizAnswerRepository solvedQuizzesRepository;

  public Optional<UserQuizAttempt> getById(Long id) {
    return solvedQuizzesRepository.findById(id);
  }

  public List<UserQuizAttempt> getAll() {
    return solvedQuizzesRepository.findAll();
  }

  public List<UserQuizAttempt> getByUser(Long user) {
    return solvedQuizzesRepository.findByUserId(user);
  }

  public List<UserQuizAttempt> getByQuiz(Long quiz) {
    return solvedQuizzesRepository.findByQuestionQuizId(quiz);
  }

  public List<UserQuizAttempt> getByQuestion(Long question) {
    return solvedQuizzesRepository.findByQuestionId(question);
  }

  public List<UserQuizAttempt> getByUserAndQuiz(Long user, Long quiz) {
    return solvedQuizzesRepository.findByUserIdAndQuestionQuizId(user, quiz);
  }

  public List<UserQuizAttempt> getByUserAndQuizAndQuestion(Long user, Long quiz, Long question) {
    return solvedQuizzesRepository.findByUserIdAndQuestionQuizIdAndQuestionId(user, quiz, question);
  }

  public UserQuizAttempt save(UserQuizAttempt solvedQuizzes) {
    return solvedQuizzesRepository.save(solvedQuizzes);
  }

  public void deleteById(Long id) {
    solvedQuizzesRepository.deleteById(id);
  }
}
