package com.wap.quizit.web.rest;

import com.wap.quizit.model.Role;
import com.wap.quizit.service.RoleService;
import com.wap.quizit.service.dto.RoleDTO;
import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.RoleMapper;
import com.wap.quizit.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleRestController {

  private RoleService roleService;
  private RoleMapper roleMapper;

  @GetMapping("/{id}")
  public ResponseEntity<RoleDTO> get(@PathVariable Long id) {
    var tmp = roleService.getById(id);
    if(tmp.isPresent()) {
      return new ResponseEntity<>(roleMapper.map(tmp.get()), HttpStatus.OK);
    } else {
      throw new EntityNotFoundException(Role.class, id);
    }
  }

  @GetMapping
  public ResponseEntity<List<RoleDTO>> getAll() {
    List<RoleDTO> list = roleService.getAll().stream().map(roleMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO dto) {
    if(roleService.getByName(dto.getName()).isPresent()) {
      throw new EntityFieldValidationException(Role.class.getSimpleName(), "name", dto.getName(), "Value already in use!");
    }
    Role role = new Role(Constants.DEFAULT_ID, dto.getName(), new HashSet<>());
    checkConditions(role, dto);
    var saved = roleService.save(role);
    return new ResponseEntity<>(roleMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<RoleDTO> update(@RequestBody RoleDTO dto) {
    if(roleService.getById(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Role.class, dto.getId());
    }
    Role role = roleMapper.map(dto);
    checkConditions(role, dto);
    var saved = roleService.save(role);
    return new ResponseEntity<>(roleMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    roleService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  protected void checkConditions(Role role, RoleDTO dto) {
    if(role.getName().length() > 20) {
      throw new EntityFieldValidationException(
          Role.class.getSimpleName(), "name", dto.getName(), "Name is too long! Maximum 20 characters.");
    }
  }
}
