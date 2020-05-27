package com.wap.quizit.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = FileResponse.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class FileResponse {
  String name;
  String uri;
  String type;
  long size;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
