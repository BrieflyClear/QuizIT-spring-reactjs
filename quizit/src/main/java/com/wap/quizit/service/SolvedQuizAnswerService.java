package com.wap.quizit.service;

import com.wap.quizit.model.SolvedQuizAnswer;
import com.wap.quizit.repository.SolvedQuizAnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SolvedQuizAnswerService {

  private SolvedQuizAnswerRepository solvedQuizzesRepository;

  public Optional<SolvedQuizAnswer> getById(Long id) {
    return solvedQuizzesRepository.findById(id);
  }

  public List<SolvedQuizAnswer> getAll() {
    return solvedQuizzesRepository.findAll();
  }

  public List<SolvedQuizAnswer> getByUser(Long user) {
    return solvedQuizzesRepository.findByUserId(user);
  }

  public List<SolvedQuizAnswer> getByQuiz(Long quiz) {
    return solvedQuizzesRepository.findByQuestionQuizId(quiz);
  }

  public List<SolvedQuizAnswer> getByQuestion(Long question) {
    return solvedQuizzesRepository.findByQuestionId(question);
  }

  public List<SolvedQuizAnswer> getByUserAndQuiz(Long user, Long quiz) {
    return solvedQuizzesRepository.findByUserIdAndQuestionQuizId(user, quiz);
  }

  public List<SolvedQuizAnswer> getByUserAndQuizAndQuestion(Long user, Long quiz, Long question) {
    return solvedQuizzesRepository.findByUserIdAndQuestionQuizIdAndQuestionId(user, quiz, question);
  }

  public SolvedQuizAnswer save(SolvedQuizAnswer solvedQuizzes) {
    return solvedQuizzesRepository.save(solvedQuizzes);
  }

  public void deleteById(Long id) {
    solvedQuizzesRepository.deleteById(id);
  }
}
