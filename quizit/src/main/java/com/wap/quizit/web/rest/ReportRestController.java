package com.wap.quizit.web.rest;

import com.wap.quizit.model.Report;
import com.wap.quizit.service.ReportService;
import com.wap.quizit.service.dto.ReportDTO;
import com.wap.quizit.service.exception.EntityNotFoundException;
import com.wap.quizit.service.mapper.ReportMapper;
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
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ReportRestController {

  private ReportService reportService;
  private ReportMapper reportMapper;

  @GetMapping("/{id}")
  public ResponseEntity<ReportDTO> get(@PathVariable Long id) {
    return new ResponseEntity<>(reportMapper.map(reportService.getById(id)), HttpStatus.OK);
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
    report.setIssuedTime(LocalDateTime.now());
    DataValidator.validateReport(report);
    var saved = reportService.save(report);
    return new ResponseEntity<>(reportMapper.map(saved), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ReportDTO> update(@RequestBody ReportDTO dto) {
    if(reportService.getByIdNoException(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Report.class, dto.getId());
    }
    Report report = reportMapper.map(dto);
    DataValidator.validateReport(report);
    var saved = reportService.save(report);
    return new ResponseEntity<>(reportMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    reportService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
