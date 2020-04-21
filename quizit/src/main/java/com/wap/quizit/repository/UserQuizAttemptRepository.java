package com.wap.quizit.repository;

import com.wap.quizit.model.UserQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizAttemptRepository extends JpaRepository<UserQuizAttempt, Long> {

  List<UserQuizAttempt> findByAnswersId(Long quizAttemptAnswerId);
  List<UserQuizAttempt> findByUserId(Long userId);
  List<UserQuizAttempt> findByQuizId(Long quizId);
  List<UserQuizAttempt> findByAnswersQuestionId(Long questionId);
  List<UserQuizAttempt> findByUserIdAndQuizId(Long userId, Long quizId);
  List<UserQuizAttempt> findByUserIdAndQuizIdAndAnswersQuestionId(Long userId, Long quizId, Long questionId);
}
