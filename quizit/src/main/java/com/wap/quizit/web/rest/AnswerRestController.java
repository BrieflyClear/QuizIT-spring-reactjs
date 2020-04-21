package com.wap.quizit.web.rest;

import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Question;
import com.wap.quizit.service.AnswerService;
import com.wap.quizit.service.dto.AnswerDTO;
import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.AnswerMapper;
import com.wap.quizit.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/answers")
@AllArgsConstructor
public class AnswerRestController {

  private AnswerService answerService;
  private AnswerMapper answerMapper;

  @GetMapping("/{id}")
  public ResponseEntity<AnswerDTO> getAnswer(@PathVariable Long id) {
    var tmp = answerService.getById(id);
    if(tmp.isPresent()) {
      return new ResponseEntity<>(answerMapper.map(tmp.get()), HttpStatus.OK);
    } else {
      throw new EntityNotFoundException(Answer.class, id);
    }
  }

  @GetMapping
  public ResponseEntity<List<AnswerDTO>> getAll() {
    List<AnswerDTO> list = answerService.getAll().stream().map(answerMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<AnswerDTO> create(@RequestBody AnswerDTO dto) {
    Answer answer = answerMapper.map(dto);
    answer.setId(Constants.DEFAULT_ID);
    checkConditions(answer, dto);
    var saved = answerService.save(answer);
    return new ResponseEntity<>(answerMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<AnswerDTO> update(@RequestBody AnswerDTO dto) {
    if(answerService.getById(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Answer.class, dto.getId());
    }
    Answer answer = answerMapper.map(dto);
    checkConditions(answer, dto);
    var saved = answerService.save(answer);
    return new ResponseEntity<>(answerMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    answerService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  protected void checkConditions(Answer answer, AnswerDTO dto) {
    if(answer.getQuestion() == null) {
      throw new EntityNotFoundException(Question.class, dto.getQuestion());
    }
    if(answer.isCorrect() && answer.getPointsCount() <= 0) {
      throw new EntityFieldValidationException(Answer.class.getSimpleName(),
          "pointsCount", dto.getPointsCount(), "Correct answer must give more than 0 points!");
    }
    if(!answer.isCorrect() && answer.getPointsCount() > 0) {
      throw new EntityFieldValidationException(Answer.class.getSimpleName(),
          "pointsCount", dto.getPointsCount(), "Not correct answer must give 0 or less points!");
    }
  }
}
