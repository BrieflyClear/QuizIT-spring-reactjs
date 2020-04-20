package com.wap.quizit.repository;

import com.wap.quizit.model.QuizCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizCategoryRepository extends JpaRepository<QuizCategory, Long> {

  Optional<QuizCategory> findByQuizIdAndCategoryId(Long quizId, Long categoryId);
}
