package com.wap.quizit.web.rest;

import com.wap.quizit.model.Quiz;
import com.wap.quizit.model.Report;
import com.wap.quizit.model.Role;
import com.wap.quizit.model.User;
import com.wap.quizit.service.ReportService;
import com.wap.quizit.service.dto.ReportDTO;
import com.wap.quizit.service.exception.EntityFieldValidationException;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.ReportMapper;
import com.wap.quizit.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ReportRestController {

  private ReportService reportService;
  private ReportMapper reportMapper;

  @GetMapping("/{id}")
  public ResponseEntity<ReportDTO> get(@PathVariable Long id) {
    var tmp = reportService.getById(id);
    if(tmp.isPresent()) {
      return new ResponseEntity<>(reportMapper.map(tmp.get()), HttpStatus.OK);
    } else {
      throw new EntityNotFoundException(Report.class, id);
    }
  }

  @GetMapping
  public ResponseEntity<List<ReportDTO>> getAll() {
    List<ReportDTO> list = reportService.getAll().stream().map(reportMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ReportDTO> create(@RequestBody ReportDTO dto) {
    Report report = reportMapper.map(dto);
    report.setId(Constants.DEFAULT_ID);
    var saved = reportService.save(report);
    return new ResponseEntity<>(reportMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ReportDTO> update(@RequestBody ReportDTO dto) {
    if(reportService.getById(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Report.class, dto.getId());
    }
    Report report = reportMapper.map(dto);
    var saved = reportService.save(report);
    return new ResponseEntity<>(reportMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    reportService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  protected void checkConditions(Report report, ReportDTO dto) {
    if(report.getReportingUser() == null) {
      throw new EntityNotFoundException(User.class, dto.getReportingUser());
    }
    if(report.getReportedQuiz() == null) {
      throw new EntityNotFoundException(Quiz.class, dto.getReportedQuiz());
    }
    // TODO change to defined values. The same in other RestControllers
    if(report.getTitle().length() > 50) {
      throw new EntityFieldValidationException(
          Report.class.getSimpleName(), "title", dto.getTitle(), "Title is too long! Maximum 50 characters.");
    }
    if(report.getDescription().length() > 200) {
      throw new EntityFieldValidationException(
          Report.class.getSimpleName(), "description", dto.getDescription(), "Description is too long! Maximum 200 characters.");
    }
    if(report.getStatus().length() > 20) {
      throw new EntityFieldValidationException(
          Report.class.getSimpleName(), "status", dto.getStatus(), "Status is too long! Maximum 20 characters.");
    }
  }
}
