package com.wap.quizit.repository;

import com.wap.quizit.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

  List<Quiz> findByTitleContainingIgnoreCase(String titleFragment);
  List<Quiz> findByTitleContainingIgnoreCaseAndIsPublic(String titleFragment, boolean isPublic);
  List<Quiz> findByCategoriesCategoryIdAndIsPublic(Long categoryId, boolean isPublic);
  List<Quiz> findAllByIsPublic(boolean isPublic);
  Optional<Quiz> findByIdAndIsPublic(Long id, boolean isPublic);
  List<Quiz> findByCategoriesCategoryId(Long categoryId);
}
