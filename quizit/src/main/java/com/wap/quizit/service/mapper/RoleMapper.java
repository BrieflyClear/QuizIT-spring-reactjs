package com.wap.quizit.service.mapper;

import com.wap.quizit.service.dto.RoleDTO;
import com.wap.quizit.service.mapper.decorator.RoleMapperDecorator;
import com.wap.quizit.model.Role;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(RoleMapperDecorator.class)
public interface RoleMapper {

  RoleDTO map(Role entity);

  @Mapping(target = "users", ignore = true)
  Role map(RoleDTO dto);
}
