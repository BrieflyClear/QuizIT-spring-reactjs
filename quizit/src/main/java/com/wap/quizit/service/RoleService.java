package com.wap.quizit.service;

import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Role;
import com.wap.quizit.repository.RoleRepository;
import com.wap.quizit.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {
  
  private RoleRepository roleRepository;

  public Optional<Role> getById(Long id) {
    return roleRepository.findById(id);
  }

  public Optional<Role> getByName(String name) {
    return roleRepository.findByName(name);
  }

  public List<Role> getAll() {
    return roleRepository.findAll();
  }

  public Role save(Role role) {
    return roleRepository.save(role);
  }

  public void deleteById(Long id) {
    roleRepository.deleteById(id);
  }
}
