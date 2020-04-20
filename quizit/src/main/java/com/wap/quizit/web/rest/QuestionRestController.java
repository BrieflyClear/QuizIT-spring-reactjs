package com.wap.quizit.web.rest;

import com.wap.quizit.model.Question;
import com.wap.quizit.service.QuestionService;
import com.wap.quizit.service.dto.QuestionDTO;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.QuestionMapper;
import com.wap.quizit.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/questions")
@AllArgsConstructor
public class QuestionRestController {

  private QuestionService questionService;
  private QuestionMapper questionMapper;

  @GetMapping("/{id}")
  public ResponseEntity<QuestionDTO> get(@PathVariable Long id) {
    var tmp = questionService.getById(id);
    if(tmp.isPresent()) {
      return new ResponseEntity<>(questionMapper.map(tmp.get()), HttpStatus.OK);
    } else {
      throw new EntityNotFoundException(Question.class, id);
    }
  }

  @GetMapping
  public ResponseEntity<List<QuestionDTO>> getAll() {
    List<QuestionDTO> list = questionService.getAll().stream().map(questionMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<QuestionDTO> create(@RequestBody QuestionDTO dto) {
    Question question = questionMapper.map(dto);
    question.setId(Constants.DEFAULT_ID);
    var saved = questionService.save(question);
    return new ResponseEntity<>(questionMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<QuestionDTO> update(@RequestBody QuestionDTO dto) {
    if(questionService.getById(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Question.class, dto.getId());
    }
    Question question = questionMapper.map(dto);
    var saved = questionService.save(question);
    return new ResponseEntity<>(questionMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    questionService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
