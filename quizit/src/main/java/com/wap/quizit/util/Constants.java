package com.wap.quizit.util;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

  public static final Long DEFAULT_ID = 0L;
  public static final Long ROLE_USER = 1L;

  public static final String USERNAME_REGEX = "^[_.@A-Za-z0-9-]*$";
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";

  public static final String ADMIN = "ROLE_ADMIN";
  public static final String USER = "ROLE_USER";
  public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  public static String getApplicationName() {
    return applicationName;
  }


  @Value("${com.wap.quizit}")
  private static String applicationName;

  private Constants(){}
}
