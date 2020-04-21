package com.wap.quizit.web.rest;

import com.wap.quizit.model.Question;
import com.wap.quizit.model.UserQuizAttempt;
import com.wap.quizit.model.User;
import com.wap.quizit.service.UserQuizAttemptService;
import com.wap.quizit.service.dto.UserQuizAttemptAnswerDTO;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.UserQuizAttemptMapper;
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

  private UserQuizAttemptService userQuizAttemptService;
  private UserQuizAttemptMapper userQuizAttemptMapper;

  @GetMapping
  public ResponseEntity<List<UserQuizAttemptAnswerDTO>> getAll() {
    List<UserQuizAttemptAnswerDTO> list = userQuizAttemptService.getAll()
        .stream().map(userQuizAttemptMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserQuizAttemptAnswerDTO> get(@PathVariable Long id) {
    var tmp = userQuizAttemptService.getById(id);
    if(tmp.isPresent()) {
      return new ResponseEntity<>(userQuizAttemptMapper.map(tmp.get()), HttpStatus.OK);
    } else {
      throw new EntityNotFoundException(UserQuizAttempt.class, id);
    }
  }

  @GetMapping("/{userId}/{quizId}")
  public ResponseEntity<List<UserQuizAttemptAnswerDTO>> getByUserAndSolvedQuiz(
      @PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId) {
    List<UserQuizAttempt> list = userQuizAttemptService.getByUserAndQuiz(userId, quizId);
    List<UserQuizAttemptAnswerDTO> listDto = list.stream().map(userQuizAttemptMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(listDto, HttpStatus.OK);
  }

  @GetMapping("/{userId}/{quizId}/{questionId}")
  public ResponseEntity<List<UserQuizAttemptAnswerDTO>> getQuestionAnswersForQuiz(
      @PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId, @PathVariable("questionId") Long questionId) {
    List<UserQuizAttempt> list = userQuizAttemptService.getByUserAndQuizAndQuestion(userId, quizId, questionId);
    List<UserQuizAttemptAnswerDTO> listDto = list.stream().map(userQuizAttemptMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(listDto, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserQuizAttemptAnswerDTO> create(@RequestBody UserQuizAttemptAnswerDTO dto) {
    UserQuizAttempt userQuizAttempt = userQuizAttemptMapper.map(dto);
    userQuizAttempt.setId(Constants.DEFAULT_ID);
    checkConditions(userQuizAttempt, dto);
    var saved = userQuizAttemptService.save(userQuizAttempt);
    return new ResponseEntity<>(userQuizAttemptMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<UserQuizAttemptAnswerDTO> update(@RequestBody UserQuizAttemptAnswerDTO dto) {
    if(userQuizAttemptService.getById(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(UserQuizAttempt.class, dto.getId());
    }
    UserQuizAttempt userQuizAttempt = userQuizAttemptMapper.map(dto);
    checkConditions(userQuizAttempt, dto);
    var saved = userQuizAttemptService.save(userQuizAttempt);
    return new ResponseEntity<>(userQuizAttemptMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    userQuizAttemptService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  protected void checkConditions(UserQuizAttempt userQuizAttempt, UserQuizAttemptAnswerDTO dto) {
    if(userQuizAttempt.getQuestion() == null) {
      throw new EntityNotFoundException(Question.class, dto.getQuestion());
    }
    if(userQuizAttempt.getUser() == null) {
      throw new EntityNotFoundException(User.class, dto.getUser());
    }
  }
}
