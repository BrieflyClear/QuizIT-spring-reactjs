package com.wap.quizit.service;

import com.wap.quizit.model.Report;
import com.wap.quizit.repository.ReportRepository;
import com.wap.quizit.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportService {
  
  private ReportRepository reportRepository;

  public Report getById(Long id) {
    return reportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Report.class, id));
  }

  public Optional<Report> getByIdNoException(Long id) {
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
