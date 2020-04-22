package com.wap.quizit.web.rest;

import com.wap.quizit.model.Answer;
import com.wap.quizit.model.Question;
import com.wap.quizit.service.AnswerService;
import com.wap.quizit.service.QuestionService;
import com.wap.quizit.service.dto.AnswerDTO;
import com.wap.quizit.service.dto.CreateAnswerDTO;
import com.wap.quizit.service.dto.CreateQuestionDTO;
import com.wap.quizit.service.dto.QuestionDTO;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.AnswerMapper;
import com.wap.quizit.service.mapper.QuestionMapper;
import com.wap.quizit.util.Constants;
import com.wap.quizit.util.DataValidator;
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
  private AnswerService answerService;
  private AnswerMapper answerMapper;

  @GetMapping("/{id}")
  public ResponseEntity<QuestionDTO> get(@PathVariable Long id) {
    return new ResponseEntity<>(questionMapper.map(questionService.getById(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<QuestionDTO>> getAll() {
    List<QuestionDTO> list = questionService.getAll().stream().map(questionMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<QuestionDTO> create(@RequestBody CreateQuestionDTO dto) {
    Question question = questionMapper.map(dto);
    question.setId(Constants.DEFAULT_ID);
    DataValidator.validateQuestion(question);
    var saved = questionService.save(question);
    return new ResponseEntity<>(questionMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<QuestionDTO> update(@RequestBody QuestionDTO dto) {
    if(questionService.getByIdNoException(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Question.class, dto.getId());
    }
    Question question = questionMapper.map(dto);
    DataValidator.validateQuestion(question);
    var saved = questionService.save(question);
    return new ResponseEntity<>(questionMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    questionService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{questionId}/answers/{answerId}")
  public ResponseEntity<AnswerDTO> getAnswer(@PathVariable("questionId") Long questionId,
                                             @PathVariable("answerId") Long answerId) {
    var question = questionService.getById(questionId);
    Answer found = question.getAnswers().stream().filter(it -> it.getId().equals(answerId)).findFirst()
        .orElseThrow(() -> new EntityNotFoundException(Answer.class, answerId, Question.class, questionId));
    return new ResponseEntity<>(answerMapper.map(found), HttpStatus.OK);
  }

  @GetMapping("/{questionId}/answers")
  public ResponseEntity<List<AnswerDTO>> getAnswers(@PathVariable("questionId") Long questionId) {
    List<AnswerDTO> list = questionService.getById(questionId).getAnswers()
        .stream().map(answerMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping("/{questionId}/answers")
  public ResponseEntity<AnswerDTO> addAnswer(@RequestBody CreateAnswerDTO dto,
                                             @PathVariable("questionId") Long questionId) {
    Question question = questionService.getById(questionId);
    var answer = answerMapper.map(dto);
    answer.setQuestion(question);
    DataValidator.validateQuestion(question);
    return new ResponseEntity<>(answerMapper.map(answerService.save(answer)), HttpStatus.OK);
  }

  @PutMapping("/{questionId}/answers")
  public ResponseEntity<AnswerDTO> updateAnswer(@RequestBody AnswerDTO dto,
                                                @PathVariable("questionId") Long questionId) {
    if(answerService.getByIdNoException(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Answer.class, dto.getId());
    }
    Answer answer = answerMapper.map(dto);
    var question = questionService.getById(questionId);
    if(question.getAnswers().stream().noneMatch(it -> it.getId().equals(answer.getId()))) {
      throw new EntityNotFoundException(Answer.class, answer.getId(), Question.class, questionId);
    }
    DataValidator.validateQuestion(question);
    var saved = answerService.save(answer);
    return new ResponseEntity<>(answerMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{questionId}/answers/{answerId}")
  public ResponseEntity<Void> deleteAnswer(@PathVariable("questionId") Long questionId,
                                           @PathVariable("answerId") Long answerId) {
    var question = questionService.getById(questionId);
    if(question.getAnswers().stream().noneMatch(it -> it.getId().equals(answerId))) {
      throw new EntityNotFoundException(Answer.class, answerId, Question.class, questionId);
    }
    var answer = answerService.getById(answerId);
    question.removeAnswer(answer);
    DataValidator.validateQuestion(question);
    answerService.deleteById(answerId);
    return ResponseEntity.noContent().build();
  }
}
