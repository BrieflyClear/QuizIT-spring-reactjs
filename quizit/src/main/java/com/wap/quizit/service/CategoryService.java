package com.wap.quizit.service;

import com.wap.quizit.model.Category;
import com.wap.quizit.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
  
  private CategoryRepository categoryRepository;

  public Optional<Category> getById(Long id) {
    return categoryRepository.findById(id);
  }

  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  public void deleteById(Long id) {
    categoryRepository.deleteById(id);
  }
}
