package com.wap.quizit.service;

import com.wap.quizit.model.UserQuizAttempt;
import com.wap.quizit.model.UserQuizAttemptAnswer;
import com.wap.quizit.repository.UserQuizAttemptAnswerRepository;
import com.wap.quizit.repository.UserQuizAttemptRepository;
import com.wap.quizit.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserQuizAttemptService {

  private UserQuizAttemptRepository solvedQuizzesRepository;
  private UserQuizAttemptAnswerRepository answerRepository;

  public UserQuizAttempt getById(Long id) {
    return solvedQuizzesRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(UserQuizAttempt.class, id));
  }

  public Optional<UserQuizAttempt> getByIdNoError(Long id) {
    return solvedQuizzesRepository.findById(id);
  }

  public List<UserQuizAttempt> getAll() {
    return solvedQuizzesRepository.findAll();
  }

  public List<UserQuizAttempt> getByUser(Long user) {
    return solvedQuizzesRepository.findByUserId(user);
  }

  public List<UserQuizAttempt> getByQuiz(Long quiz) {
    return solvedQuizzesRepository.findByQuizId(quiz);
  }

  public List<UserQuizAttempt> getByQuestion(Long question) {
    return solvedQuizzesRepository.findByAttemptAnswersQuestionId(question);
  }

  public List<UserQuizAttempt> getByUserAndQuiz(Long user, Long quiz) {
    return solvedQuizzesRepository.findByUserIdAndQuizId(user, quiz);
  }

  public UserQuizAttemptAnswer getQuizAttemptAnswerById(Long quizAttemptAnswer) {
    return answerRepository.findById(quizAttemptAnswer)
        .orElseThrow(() -> new EntityNotFoundException(UserQuizAttemptAnswer.class, quizAttemptAnswer));
  }

  public Optional<UserQuizAttemptAnswer> getQuizAttemptAnswerByIdNoError(Long quizAttemptAnswer) {
    return answerRepository.findById(quizAttemptAnswer);
  }

  public UserQuizAttempt save(UserQuizAttempt solvedQuizzes) {
    return solvedQuizzesRepository.save(solvedQuizzes);
  }

  public UserQuizAttemptAnswer saveAnswer(UserQuizAttemptAnswer solvedQuizzes) {
    return answerRepository.save(solvedQuizzes);
  }

  public void deleteById(Long id) {
    solvedQuizzesRepository.deleteById(id);
  }

  public void deleteAnswerById(Long id) {
    answerRepository.deleteById(id);
  }
}
