package com.wap.quizit.service;

import com.wap.quizit.model.Report;
import com.wap.quizit.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportService {
  
  private ReportRepository reportRepository;

  public Optional<Report> getById(Long id) {
    return reportRepository.findById(id);
  }

  public List<Report> getAll() {
    return reportRepository.findAll();
  }

  public Report save(Report report) {
    return reportRepository.save(report);
  }

  public void deleteById(Long id) {
    reportRepository.deleteById(id);
  }
}
