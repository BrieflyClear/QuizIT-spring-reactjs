package com.wap.quizit.web.rest;

import com.wap.quizit.model.User;
import com.wap.quizit.service.UserService;
import com.wap.quizit.service.dto.LoginUserDTO;
import com.wap.quizit.service.dto.RegisterUserDTO;
import com.wap.quizit.service.dto.UserDTO;
import com.wap.quizit.service.exception.*;
import com.wap.quizit.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    var tmp = userService.getById(id);
    if(tmp.isPresent()) {
      return new ResponseEntity<>(userMapper.map(tmp.get()), HttpStatus.OK);
    } else {
      throw new EntityNotFoundException(User.class, id);
    }
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAll() {
    var list = userService.getAll().stream().map(userMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(@RequestBody RegisterUserDTO form) {
    if(userService.getByUsername(form.getUsername()).isPresent()) {
      throw new UsernameAlreadyUsedException(form.getUsername());
    }
    if(userService.getByEmail(form.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException();
    }
    var newUser = userService.registerUser(form);
    return new ResponseEntity<>(userMapper.map(newUser), HttpStatus.OK);
  }

  @GetMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestBody LoginUserDTO loginForm) {
    var tmpUser = userService.getByEmail(loginForm.getEmail());
    if(tmpUser.isEmpty()) {
      throw new UserNotExistsException(loginForm.getEmail());
    }
    if(!tmpUser.get().getPassword().equals(loginForm.getPassword())) {
      throw new InvalidPasswordException();
    }
    return new ResponseEntity<>(userMapper.map(tmpUser.get()), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto) {
    if(userService.getById(dto.getId()).isPresent()) {
      var user = userMapper.map(dto);
      var saved = userService.save(user);
      return new ResponseEntity<>(userMapper.map(saved), HttpStatus.OK);
    } else {
      throw new UserNotExistsException(dto.getId());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
