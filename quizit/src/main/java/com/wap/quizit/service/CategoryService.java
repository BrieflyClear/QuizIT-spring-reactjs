package com.wap.quizit.service;

import com.wap.quizit.model.Category;
import com.wap.quizit.model.Question;
import com.wap.quizit.repository.CategoryRepository;
import com.wap.quizit.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
  
  private CategoryRepository categoryRepository;

  public Category getById(Long id) {
    return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Category.class, id));
  }

  public Optional<Category> getByIdNoException(Long id) {
    return categoryRepository.findById(id);
  }

  public List<Category> getAll() {
    return categoryRepository.findAll();
  }

  public Category getByName(String name) {
    return categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(Category.class, name));
  }

  public Optional<Category> getByNameNoException(String name) {
    return categoryRepository.findByName(name);
  }

  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  public void deleteById(Long id) {
    categoryRepository.deleteById(id);
  }
}
