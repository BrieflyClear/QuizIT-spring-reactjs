package com.wap.quizit.repository;

import com.wap.quizit.model.SolvedQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolvedQuizAnswerRepository extends JpaRepository<SolvedQuizAnswer, Long> {

  List<SolvedQuizAnswer> findByQuestionQuizId(Long quizId);
  List<SolvedQuizAnswer> findByQuestionId(Long questionId);
  List<SolvedQuizAnswer> findByUserId(Long questionId);
  List<SolvedQuizAnswer> findByUserIdAndQuestionId(Long userId, Long questionId);
  List<SolvedQuizAnswer> findByUserIdAndQuestionQuizId(Long userId, Long quizId);
  List<SolvedQuizAnswer> findByUserIdAndQuestionQuizIdAndQuestionId(Long userId, Long quizId, Long questionId);
}
