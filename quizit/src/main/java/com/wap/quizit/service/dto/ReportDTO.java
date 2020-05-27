package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@JsonDeserialize(builder = ReportDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class ReportDTO {

  @NotNull Long id;

  @NotNull @NotBlank String title;

  @NotNull @NotBlank String description;

  @NotNull @NotBlank String status;

  @NotNull @NotBlank String issuedTime;

  @NotNull Long reportedQuiz;

  @NotNull Long reportingUser;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
