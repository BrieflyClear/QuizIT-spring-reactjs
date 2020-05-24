package com.wap.quizit.web.rest;

import com.wap.quizit.model.Quiz;
import com.wap.quizit.service.FileSystemStorageService;
import com.wap.quizit.service.QuizService;
import com.wap.quizit.service.UserService;
import com.wap.quizit.service.dto.QuizDTO;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.QuizMapper;
import com.wap.quizit.util.Constants;
import com.wap.quizit.util.DataValidator;
import com.wap.quizit.util.JsonToQuizParser;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/quizzes")
@AllArgsConstructor
public class QuizRestController {

  private QuizService quizService;
  private QuizMapper quizMapper;
  private FileSystemStorageService storageService;
  private JsonToQuizParser jsonToQuizParser;
  private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<QuizDTO> get(@PathVariable Long id,
                                     @RequestParam(value = "include_private", required = false,
                                         defaultValue = "false") boolean includePrivate) {
    return new ResponseEntity<>(quizMapper.map(quizService.getById(id, includePrivate)), HttpStatus.OK);
  }

  @GetMapping("/title/{titleFragment}")
  public ResponseEntity<List<QuizDTO>> getByTitle(@PathVariable String titleFragment,
                                                  @RequestParam(value = "include_private", required = false,
                                                      defaultValue = "false") boolean includePrivate) {
    List<QuizDTO> list = quizService.getByTitleFragment(titleFragment, includePrivate).stream().map(quizMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<QuizDTO>> getByCategoryId(@PathVariable Long categoryId,
                                                       @RequestParam(value = "include_private", required = false,
                                                           defaultValue = "false") boolean includePrivate) {
    List<QuizDTO> list = quizService.getByCategoryId(categoryId, includePrivate).stream().map(quizMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<QuizDTO>> getAll(@RequestParam(value = "include_private", required = false,
      defaultValue = "false") boolean includePrivate) {
    List<QuizDTO> list = quizService.getAll(includePrivate).stream().map(quizMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<QuizDTO> create(@RequestBody QuizDTO dto) {
    Quiz quiz = quizMapper.map(dto);
    quiz.setId(Constants.DEFAULT_ID);
    DataValidator.validateQuiz(quiz);
    var saved = quizService.save(quiz);
    return new ResponseEntity<>(quizMapper.map(saved), HttpStatus.OK);
  }

  @PostMapping("/fromFile")
  public ResponseEntity<QuizDTO> loadFromFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userId") Long userId) throws IOException {
    String name = storageService.store(file);
    var quiz = jsonToQuizParser.parseFileFromStorageToQuiz(name);
    quiz.setAuthor(userService.getById(userId));
    DataValidator.validateQuiz(quiz);
    var saved = quizService.save(quiz);
    return new ResponseEntity<>(quizMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<QuizDTO> update(@RequestBody QuizDTO dto) {
    if(quizService.getByIdNoException(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Quiz.class, dto.getId());
    }
    Quiz quiz = quizMapper.map(dto);
    DataValidator.validateQuiz(quiz);
    var saved = quizService.save(quiz);
    return new ResponseEntity<>(quizMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    quizService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/download")
  public ResponseEntity<Resource> downloadQuiz(@PathVariable("id") Long id) throws IOException {
    var quiz = quizService.getById(id, true);
    String fileName = jsonToQuizParser.parseQuizToJsonFile(quiz);
    Resource resource = storageService.loadAsResource(fileName);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}
