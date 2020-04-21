package com.wap.quizit.service.mapper.decorator;

import com.wap.quizit.service.dto.RoleDTO;
import com.wap.quizit.service.mapper.RoleMapper;
import com.wap.quizit.model.Role;
import com.wap.quizit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;

public abstract class RoleMapperDecorator implements RoleMapper {

  @Autowired
  @Qualifier("delegate")
  private RoleMapper delegate;
  @Autowired
  private RoleService roleService;

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
