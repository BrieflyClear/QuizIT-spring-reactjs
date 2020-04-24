package com.wap.quizit.service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wap.quizit.util.Constants;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApplicationInfoDTO {

  String version = Constants.getApplicationVersion();
  String name = Constants.getApplicationName();

  Long role_user_id = Constants.ROLE_USER_ID;
  Long role_admin_id = Constants.ROLE_ADMIN_ID;

  String username_regex = Constants.USERNAME_REGEX;
  String email_regex = Constants.EMAIL_REGEX;
  String password_regex = Constants.PASSWORD_REGEX;
  Integer email_min_length = Constants.MIN_EMAIL_LENGTH;
  Integer email_max_length = Constants.MAX_EMAIL_LENGTH;
  String role_regex = Constants.ROLE_REGEX;
  String report_title_regex = Constants.REPORT_TITLE_REGEX;
  String report_description_regex = Constants.REPORT_DESCRIPTION_REGEX;
  String report_status_regex = Constants.REPORT_STATUS_REGEX;
  String category_regex = Constants.CATEGORY_REGEX;
  String comment_regex = Constants.COMMENT_REGEX;
  String quiz_regex = Constants.QUIZ_REGEX;
  String question_regex = Constants.QUESTION_REGEX;
  String answer_regex = Constants.ANSWER_REGEX;

  Integer answer_max_point_count = Constants.ANSWER_MAX_POINT_COUNT;
  Integer answer_min_point_count = Constants.ANSWER_MIN_POINT_COUNT;
  Integer open_question_answer_point_count = Constants.OPEN_QUESTION_ANSWER_POINTS;
}
