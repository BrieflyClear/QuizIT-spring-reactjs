package com.wap.quizit.service.dto;

import com.wap.quizit.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
public class UserDTO {

  @NotNull Long id;

  @NotNull @NotBlank
  @Pattern(regexp = Constants.USERNAME_REGEX)
  @Size(min = 1, max = 50)
  String username;

  @NotNull Boolean isPremium;

  @NotNull Long role;

  @NotNull List<Long> quizzes;

  @NotNull List<Long> comments;

  @NotNull List<Long> reportsIssued;
}
