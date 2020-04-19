package com.wap.quizit.mapper;

import com.wap.quizit.dto.RegisterUserDTO;
import com.wap.quizit.dto.UserDTO;
import com.wap.quizit.mapper.decorator.UserMapperDecorator;
import com.wap.quizit.model.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

  UserDTO map(User entity);
  User map(UserDTO dto);

  User map(RegisterUserDTO registerForm);
}
