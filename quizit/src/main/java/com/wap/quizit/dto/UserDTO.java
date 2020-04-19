package com.wap.quizit.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserDTO {

  Long id;
  String username;
  Long role;
  List<Long> quizzes;
  List<Long> comments;
  List<Long> reportsIssued;
}
