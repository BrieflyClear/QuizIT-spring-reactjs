package com.wap.quizit.web.rest;

import com.wap.quizit.model.Category;
import com.wap.quizit.service.CategoryService;
import com.wap.quizit.service.dto.CategoryDTO;
import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.CategoryMapper;
import com.wap.quizit.util.Constants;
import com.wap.quizit.util.DataValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryRestController {

  private CategoryService categoryService;
  private CategoryMapper categoryMapper;

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> get(@PathVariable Long id) {
    return new ResponseEntity<>(categoryMapper.map(categoryService.getById(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAll() {
    List<CategoryDTO> list = categoryService.getAll().stream().map(categoryMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto) {
    if(categoryService.getByNameNoException(dto.getName()).isPresent()) {
      throw new EntityFieldValidationException(
          Category.class.getSimpleName(), "name", dto.getName(), "Value already in use!");
    }
    Category category = categoryMapper.map(dto);
    category.setId(Constants.DEFAULT_ID);
    DataValidator.validateCategory(category);
    var saved = categoryService.save(category);
    return new ResponseEntity<>(categoryMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO dto) {
    if(categoryService.getByIdNoException(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Category.class, dto.getId());
    }
    Category category = categoryMapper.map(dto);
    DataValidator.validateCategory(category);
    var saved = categoryService.save(category);
    return new ResponseEntity<>(categoryMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    categoryService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
