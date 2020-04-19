package com.wap.quizit.mapper;

import com.wap.quizit.dto.ReportDTO;
import com.wap.quizit.mapper.decorator.ReportMapperDecorator;
import com.wap.quizit.model.Report;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(ReportMapperDecorator.class)
public interface ReportMapper {

  @Mapping(target="issuedTime", source = "issuedTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  ReportDTO map(Report entity);

  @Mapping(target="issuedTime", source = "issuedTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  Report map(ReportDTO dto);
}
