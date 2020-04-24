package com.wap.quizit.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FileResponse {
  String name;
  String uri;
  String type;
  long size;

  public FileResponse(String name, String uri, String type, long size) {
    this.name = name;
    this.uri = uri;
    this.type = type;
    this.size = size;
  }
}
