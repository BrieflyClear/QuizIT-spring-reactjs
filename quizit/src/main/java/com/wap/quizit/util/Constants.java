package com.wap.quizit.util;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

  public static final Long DEFAULT_ID = 0L;
  public static final Long ROLE_USER_ID = 1L;

  public static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{4,15}$";
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";
  public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,15}$";
  /*public static final Integer MIN_USERNAME_LENGTH = 4;
  public static final Integer MAX_USERNAME_LENGTH = 15;
  public static final Integer MIN_PASSWORD_LENGTH = 6;
  public static final Integer MAX_PASSWORD_LENGTH = 60;*/
  public static final Integer MIN_EMAIL_LENGTH = 5;
  public static final Integer MAX_EMAIL_LENGTH = 50;


  public static final String ADMIN = "ROLE_ADMIN";
  public static final String USER = "ROLE_USER";
  public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  public static final Integer ANSWER_MAX_POINT_COUNT = 50;
  public static final Integer ANSWER_MIN_POINT_COUNT = -50;
  public static final Integer ANSWER_OPEN_POINTS = 0;

  public static String getApplicationName() {
    return applicationName;
  }


  @Value("${com.wap.quizit}")
  private static String applicationName;

  private Constants(){}
}
