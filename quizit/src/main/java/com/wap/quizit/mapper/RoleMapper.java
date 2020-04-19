package com.wap.quizit.mapper;

import com.wap.quizit.dto.RoleDTO;
import com.wap.quizit.mapper.decorator.RoleMapperDecorator;
import com.wap.quizit.model.Role;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(RoleMapperDecorator.class)
public interface RoleMapper {

  RoleDTO map(Role entity);
  Role map(RoleDTO dto);
}
