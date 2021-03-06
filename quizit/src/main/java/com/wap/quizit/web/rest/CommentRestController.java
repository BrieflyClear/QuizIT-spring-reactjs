package com.wap.quizit.web.rest;

import com.wap.quizit.model.Comment;
import com.wap.quizit.model.Question;
import com.wap.quizit.model.User;
import com.wap.quizit.service.CommentService;
import com.wap.quizit.service.dto.CommentDTO;
import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.CommentMapper;
import com.wap.quizit.util.Constants;
import com.wap.quizit.util.DataValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentRestController {

  private CommentService commentService;
  private CommentMapper commentMapper;

  @GetMapping("/{id}")
  public ResponseEntity<CommentDTO> get(@PathVariable Long id) {
    return new ResponseEntity<>(commentMapper.map(commentService.getById(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<CommentDTO>> getAll() {
    List<CommentDTO> list = commentService.getAll().stream().map(commentMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/question/{questionId}")
  public ResponseEntity<List<CommentDTO>> getByQuestion(@PathVariable Long questionId) {
    List<CommentDTO> list = commentService.getByQuestionId(questionId).stream().map(commentMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CommentDTO> create(@RequestBody CommentDTO dto) {
    Comment comment = commentMapper.map(dto);
    comment.setId(Constants.DEFAULT_ID);
    comment.setIssuedTime(LocalDateTime.now());
    DataValidator.validateComment(comment);
    var saved = commentService.save(comment);
    return new ResponseEntity<>(commentMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<CommentDTO> update(@RequestBody CommentDTO dto) {
    if(commentService.getByIdNoException(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Comment.class, dto.getId());
    }
    Comment comment = commentMapper.map(dto);
    DataValidator.validateComment(comment);
    var saved = commentService.save(comment);
    return new ResponseEntity<>(commentMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    commentService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
