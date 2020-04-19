package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.ReportDTO;
import com.wap.quizit.mapper.ReportMapper;
import com.wap.quizit.model.Report;
import com.wap.quizit.service.QuizService;
import com.wap.quizit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class ReportMapperDecorator implements ReportMapper {

  @Autowired
  @Qualifier("delegate")
  private ReportMapper delegate;
  @Autowired
  private QuizService quizService;
  @Autowired
  private UserService userService;

  /**
   * Should throw error instead of null
   */
  @Override
  public Report map(ReportDTO dto) {
    var report = delegate.map(dto);
    report.setReportedQuiz(quizService.getById(dto.getReportedQuiz()).orElse(null));
    report.setReportingUser(userService.getById(dto.getReportingUser()).orElse(null));
    return report;
  }
}
