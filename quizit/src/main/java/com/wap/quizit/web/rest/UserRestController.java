package com.wap.quizit.web.rest;

import com.wap.quizit.model.Role;
import com.wap.quizit.model.User;
import com.wap.quizit.service.UserService;
import com.wap.quizit.service.dto.LoginUserDTO;
import com.wap.quizit.service.dto.RegisterUserDTO;
import com.wap.quizit.service.dto.UserDTO;
import com.wap.quizit.service.exception.*;
import com.wap.quizit.service.mapper.UserMapper;
import com.wap.quizit.util.Constants;
import com.wap.quizit.util.DataValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {

  private UserService userService;
  private UserMapper userMapper;

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> get(@PathVariable Long id) {
    return new ResponseEntity<>(userMapper.map(userService.getById(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAll() {
    var list = userService.getAll().stream().map(userMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(@RequestBody RegisterUserDTO form) {
    if(userService.getByUsernameNoException(form.getUsername()).isPresent()) {
      throw new UsernameAlreadyUsedException(form.getUsername());
    }
    if(userService.getByEmailNoException(form.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException();
    }
    var user = userMapper.map(form);
    DataValidator.validateUser(user);
    var newUser = userService.save(user);
    return new ResponseEntity<>(userMapper.map(newUser), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestBody LoginUserDTO loginForm) {
    var tmpUser = userService.getByEmail(loginForm.getEmail());
    if(!tmpUser.getPassword().equals(loginForm.getPassword())) {
      throw new InvalidPasswordException();
    }
    return new ResponseEntity<>(userMapper.map(tmpUser), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto) {
    if(userService.getByIdNoException(dto.getId()).isEmpty()) {
      throw new UserNotExistsException(dto.getId());
    }
    var user = userMapper.map(dto);
    DataValidator.validateUser(user);
    var saved = userService.save(user);
    return new ResponseEntity<>(userMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
