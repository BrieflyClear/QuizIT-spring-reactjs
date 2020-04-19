package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.CategoryDTO;
import com.wap.quizit.mapper.CategoryMapper;
import com.wap.quizit.model.Category;
import com.wap.quizit.model.QuizCategory;
import com.wap.quizit.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public abstract class CategoryMapperDecorator implements CategoryMapper {

  @Qualifier("delegate")
  private CategoryMapper delegate;
  private CategoryService categoryService;

  @Override
  public Category map(CategoryDTO dto) {
    var category = delegate.map(dto);
    Set<QuizCategory> quizzes = categoryService.getById(dto.getId()).map(Category::getQuizzes).orElse(new HashSet<>());
    category.setQuizzes(quizzes);
    return category;
  }
}
