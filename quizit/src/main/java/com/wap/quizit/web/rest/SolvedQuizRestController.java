package com.wap.quizit.web.rest;

import com.wap.quizit.model.Question;
import com.wap.quizit.model.UserQuizAttempt;
import com.wap.quizit.model.User;
import com.wap.quizit.service.SolvedQuizAnswerService;
import com.wap.quizit.service.dto.SolvedQuizAnswerDTO;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.SolvedQuizAnswerMapper;
import com.wap.quizit.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/solvedQuizzes")
@AllArgsConstructor
public class SolvedQuizRestController {

  private SolvedQuizAnswerService solvedQuizAnswerService;
  private SolvedQuizAnswerMapper solvedQuizAnswerMapper;

  @GetMapping
  public ResponseEntity<List<SolvedQuizAnswerDTO>> getAll() {
    List<SolvedQuizAnswerDTO> list = solvedQuizAnswerService.getAll()
        .stream().map(solvedQuizAnswerMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SolvedQuizAnswerDTO> get(@PathVariable Long id) {
    var tmp = solvedQuizAnswerService.getById(id);
    if(tmp.isPresent()) {
      return new ResponseEntity<>(solvedQuizAnswerMapper.map(tmp.get()), HttpStatus.OK);
    } else {
      throw new EntityNotFoundException(UserQuizAttempt.class, id);
    }
  }

  @GetMapping("/{userId}/{quizId}")
  public ResponseEntity<List<SolvedQuizAnswerDTO>> getByUserAndSolvedQuiz(
      @PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId) {
    List<UserQuizAttempt> list = solvedQuizAnswerService.getByUserAndQuiz(userId, quizId);
    List<SolvedQuizAnswerDTO> listDto = list.stream().map(solvedQuizAnswerMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(listDto, HttpStatus.OK);
  }

  @GetMapping("/{userId}/{quizId}/{questionId}")
  public ResponseEntity<List<SolvedQuizAnswerDTO>> getQuestionAnswersForQuiz(
      @PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId, @PathVariable("questionId") Long questionId) {
    List<UserQuizAttempt> list = solvedQuizAnswerService.getByUserAndQuizAndQuestion(userId, quizId, questionId);
    List<SolvedQuizAnswerDTO> listDto = list.stream().map(solvedQuizAnswerMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(listDto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SolvedQuizAnswerDTO> create(@RequestBody SolvedQuizAnswerDTO dto) {
    UserQuizAttempt userQuizAttempt = solvedQuizAnswerMapper.map(dto);
    userQuizAttempt.setId(Constants.DEFAULT_ID);
    checkConditions(userQuizAttempt, dto);
    var saved = solvedQuizAnswerService.save(userQuizAttempt);
    return new ResponseEntity<>(solvedQuizAnswerMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<SolvedQuizAnswerDTO> update(@RequestBody SolvedQuizAnswerDTO dto) {
    if(solvedQuizAnswerService.getById(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(UserQuizAttempt.class, dto.getId());
    }
    UserQuizAttempt userQuizAttempt = solvedQuizAnswerMapper.map(dto);
    checkConditions(userQuizAttempt, dto);
    var saved = solvedQuizAnswerService.save(userQuizAttempt);
    return new ResponseEntity<>(solvedQuizAnswerMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    solvedQuizAnswerService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  protected void checkConditions(UserQuizAttempt userQuizAttempt, SolvedQuizAnswerDTO dto) {
    if(userQuizAttempt.getQuestion() == null) {
      throw new EntityNotFoundException(Question.class, dto.getQuestion());
    }
    if(userQuizAttempt.getUser() == null) {
      throw new EntityNotFoundException(User.class, dto.getUser());
    }
  }
}
