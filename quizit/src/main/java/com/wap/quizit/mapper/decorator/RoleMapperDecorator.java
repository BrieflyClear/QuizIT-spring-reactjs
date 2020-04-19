package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.RoleDTO;
import com.wap.quizit.mapper.RoleMapper;
import com.wap.quizit.model.Role;
import com.wap.quizit.model.User;
import com.wap.quizit.service.RoleService;
import com.wap.quizit.service.QuizService;
import com.wap.quizit.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public abstract class RoleMapperDecorator implements RoleMapper {

  @Qualifier("delegate")
  private RoleMapper delegate;
  private RoleService roleService;

  /**
   * Should throw error instead of null
   */
  @Override
  public Role map(RoleDTO dto) {
    var role = delegate.map(dto);
    var savedRole = roleService.getById(dto.getId());
    if(savedRole.isPresent()) {
      role.setUsers(savedRole.get().getUsers());
    } else {
      role.setUsers(new HashSet<>());
    }
    return role;
  }
}
