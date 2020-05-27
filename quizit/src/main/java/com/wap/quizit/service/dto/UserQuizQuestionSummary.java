package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@JsonDeserialize(builder = UserQuizQuestionSummary.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserQuizQuestionSummary {

  @NotNull Long question;
  @NotNull Integer maxPointsCount;
  @NotNull Integer pointsGained;
  @NotNull List<Long> answersGiven;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
