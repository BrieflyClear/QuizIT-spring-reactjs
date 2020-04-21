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
    var newUser = userService.registerUser(form);
    return new ResponseEntity<>(userMapper.map(newUser), HttpStatus.OK);
  }

  @GetMapping("/login")
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
    checkConditions(user, dto);
    var saved = userService.save(user);
    return new ResponseEntity<>(userMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  protected void checkConditions(User user, UserDTO dto) {
    if(user.getRole() == null) {
      throw new EntityNotFoundException(Role.class, dto.getRole());
    }
    if(dto.getUsername().length() <= 3) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "username", dto.getUsername(), "Username too short! Minimum 4 characters");
    } else if(dto.getUsername().length() > 15) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "username", dto.getUsername(), "Username too long! Maximum 15 characters");
    } else if(!Pattern.matches(Constants.USERNAME_REGEX, dto.getUsername())) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "username", dto.getUsername(), "Wrong username characters!");
    }
    if(user.getPassword().length() <= 5) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "password", "---", "Password too short! Minimum 6 characters");
    }else if(user.getPassword().length() > 60) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "password", "---", "Password too long! Maximum 60 characters");
    }
    if(user.getEmail().length() <= 4) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "email", "---", "Email too short! Minimum 5 characters");
    }else if(user.getEmail().length() > 50) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "email", "---", "Email too long! Maximum 50 characters");
    } else if(!Pattern.matches(Constants.EMAIL_REGEX, user.getEmail())) {
      throw new EntityFieldValidationException(
          User.class.getSimpleName(), "email", user.getEmail(), "Wrong email characters!");
    }
  }
}
