package com.wap.quizit.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wap.quizit.model.Category;
import com.wap.quizit.model.Quiz;
import com.wap.quizit.model.QuizCategory;
import com.wap.quizit.service.CategoryService;
import com.wap.quizit.service.FileSystemStorageService;
import com.wap.quizit.service.dto.QuizFileDTO;
import com.wap.quizit.service.mapper.QuestionMapper;
import lombok.AllArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JsonToQuizParser {

  private static String FILE_PARSED = "{quizTitle}_{quizId}_"
      + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")) + ".json";

  private QuestionMapper questionMapper;
  private CategoryService categoryService;
  private FileSystemStorageService fileSystemStorageService;

  public Quiz parseFileFromStorageToQuiz(String fileName) throws IOException {
    QuizFileDTO quizFile = new ObjectMapper().readValue(new URL("file:uploads/" + fileName), QuizFileDTO.class);
    Quiz quiz = new Quiz();
    quiz.setId(Constants.DEFAULT_ID);
    Set<QuizCategory> categories = new HashSet<>();
    quizFile.getCategories().forEach(str -> categories.add(
        new QuizCategory(Constants.DEFAULT_ID, quiz, categoryService.getByName(str))));
    quiz.setCategories(categories);
    quiz.setTitle(quizFile.getTitle());
    quiz.setPublic(quizFile.getIsPublic());
    quiz.setQuestions(quizFile.getQuestions().stream().map(questionMapper::mapFromFileFormat).collect(Collectors.toSet()));
    quiz.getQuestions().forEach(it -> it.setQuiz(quiz));
    quiz.setAttempts(new HashSet<>());
    quiz.setAuthor(null);
    quiz.setReportsIssued(new HashSet<>());
    return quiz;
  }

  public String parseQuizToJsonFile(Quiz quiz) throws IOException {
    String fileName = FILE_PARSED.replace("{quizTitle}", quiz.getTitle())
        .replace("{quizId}", String.valueOf(quiz.getId()));
    String path = fileSystemStorageService.getLocation() + "/" + fileName;
    File file = new File(path);

    QuizFileDTO quizFile = new QuizFileDTO();
    quizFile.setTitle(quiz.getTitle());
    quizFile.setIsPublic(quiz.isPublic());
    quizFile.setCategories(quiz.getCategories()
        .stream().map(QuizCategory::getCategory).map(Category::getName).collect(Collectors.toList()));
    quizFile.setQuestions(quiz.getQuestions().stream().map(questionMapper::mapToFileFormat).collect(Collectors.toList()));
    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(file, quizFile);
    MultipartFile result = new MockMultipartFile(fileName, fileName, "text/plain",
        Files.readAllBytes(file.toPath()));
    fileSystemStorageService.store(result);
    return fileName;
  }
}
