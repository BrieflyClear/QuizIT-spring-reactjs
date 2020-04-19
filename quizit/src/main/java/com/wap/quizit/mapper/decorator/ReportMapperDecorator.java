package com.wap.quizit.mapper.decorator;

import com.wap.quizit.dto.ReportDTO;
import com.wap.quizit.mapper.ReportMapper;
import com.wap.quizit.model.Report;
import com.wap.quizit.service.QuizService;
import com.wap.quizit.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

@AllArgsConstructor
public abstract class ReportMapperDecorator implements ReportMapper {

  @Qualifier("delegate")
  private ReportMapper delegate;
  private QuizService quizService;
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
