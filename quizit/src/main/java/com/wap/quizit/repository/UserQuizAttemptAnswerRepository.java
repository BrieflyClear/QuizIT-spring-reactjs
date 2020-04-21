package com.wap.quizit.repository;

import com.wap.quizit.model.UserQuizAttemptAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizAttemptAnswerRepository extends JpaRepository<UserQuizAttemptAnswer, Long> {
}
