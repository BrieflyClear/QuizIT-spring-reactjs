package com.wap.quizit.web.rest;

import com.wap.quizit.model.Quiz;
import com.wap.quizit.model.Report;
import com.wap.quizit.model.User;
import com.wap.quizit.service.QuizService;
import com.wap.quizit.service.dto.QuizDTO;
import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.QuizMapper;
import com.wap.quizit.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/quizzes")
@AllArgsConstructor
public class QuizRestController {

  private QuizService quizService;
  private QuizMapper quizMapper;

  @GetMapping("/{id}")
  public ResponseEntity<QuizDTO> get(@PathVariable Long id) {
    var tmp = quizService.getById(id);
    if(tmp.isPresent()) {
      return new ResponseEntity<>(quizMapper.map(tmp.get()), HttpStatus.OK);
    } else {
      throw new EntityNotFoundException(Quiz.class, id);
    }
  }

  @GetMapping
  public ResponseEntity<List<QuizDTO>> getAll() {
    List<QuizDTO> list = quizService.getAll().stream().map(quizMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<QuizDTO> create(@RequestBody QuizDTO dto) {
    Quiz quiz = quizMapper.map(dto);
    quiz.setId(Constants.DEFAULT_ID);
    checkConditions(quiz, dto);
    var saved = quizService.save(quiz);
    return new ResponseEntity<>(quizMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<QuizDTO> update(@RequestBody QuizDTO dto) {
    if(quizService.getById(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Quiz.class, dto.getId());
    }
    Quiz quiz = quizMapper.map(dto);
    checkConditions(quiz, dto);
    var saved = quizService.save(quiz);
    return new ResponseEntity<>(quizMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    quizService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  protected void checkConditions(Quiz quiz, QuizDTO dto) {
    if(quiz.getAuthor() == null) {
      throw new EntityNotFoundException(User.class, dto.getAuthor());
    }
    if(quiz.getTitle().length() > 50) {
      throw new EntityFieldValidationException(
          Quiz.class.getSimpleName(), "title", dto.getTitle(), "Title is too long! Maximum 50 characters.");
    }
  }
}
