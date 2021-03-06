package com.wap.quizit.service.mapper;

import com.wap.quizit.service.dto.CategoryDTO;
import com.wap.quizit.service.mapper.decorator.CategoryMapperDecorator;
import com.wap.quizit.model.Category;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(CategoryMapperDecorator.class)
public interface CategoryMapper {

  CategoryDTO map(Category entity);

  @Mapping(target = "quizzes", ignore = true)
  Category map(CategoryDTO dto);
}
