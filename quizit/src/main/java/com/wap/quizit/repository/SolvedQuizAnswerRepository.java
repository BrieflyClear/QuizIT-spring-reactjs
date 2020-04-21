package com.wap.quizit.repository;

import com.wap.quizit.model.UserQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolvedQuizAnswerRepository extends JpaRepository<UserQuizAttempt, Long> {

  List<UserQuizAttempt> findByQuestionQuizId(Long quizId);
  List<UserQuizAttempt> findByQuestionId(Long questionId);
  List<UserQuizAttempt> findByUserId(Long questionId);
  List<UserQuizAttempt> findByUserIdAndQuestionId(Long userId, Long questionId);
  List<UserQuizAttempt> findByUserIdAndQuestionQuizId(Long userId, Long quizId);
  List<UserQuizAttempt> findByUserIdAndQuestionQuizIdAndQuestionId(Long userId, Long quizId, Long questionId);
}
