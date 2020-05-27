package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@JsonDeserialize(builder = CommentDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class CommentDTO {

  @NotNull Long id;
  @NotNull @NotBlank String contents;

  @NotNull @NotBlank String issuedTime;

  @NotNull Long author;

  @NotNull Long question;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
