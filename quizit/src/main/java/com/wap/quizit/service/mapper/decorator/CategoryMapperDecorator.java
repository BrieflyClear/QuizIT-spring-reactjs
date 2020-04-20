package com.wap.quizit.service.mapper.decorator;

import com.wap.quizit.service.dto.CategoryDTO;
import com.wap.quizit.service.mapper.CategoryMapper;
import com.wap.quizit.model.Category;
import com.wap.quizit.model.QuizCategory;
import com.wap.quizit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

public abstract class CategoryMapperDecorator implements CategoryMapper {

  @Autowired
  @Qualifier("delegate")
  private CategoryMapper delegate;
  @Autowired
  private CategoryService categoryService;

  @Override
  public Category map(CategoryDTO dto) {
    var category = delegate.map(dto);
    Set<QuizCategory> quizzes = categoryService.getById(dto.getId()).map(Category::getQuizzes).orElse(new HashSet<>());
    category.setQuizzes(quizzes);
    return category;
  }
}
