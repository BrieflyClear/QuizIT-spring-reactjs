package com.wap.quizit.repository;

import com.wap.quizit.model.QuizCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizCategoryRepository extends JpaRepository<QuizCategory, Long> {
}
