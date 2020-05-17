package com.wap.quizit.util;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

public class Constants {

  public static final String ADMIN = "Admin";
  public static final String USER = "User";
  public static final String ANONYMOUS = "Anonymous";

  public static final Long ROLE_ADMIN_ID = 1L;
  public static final Long ROLE_USER_ID = 2L;

  public static final Long DEFAULT_ID = 0L;

  public static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{4,15}$";
  public static final String EMAIL_REGEX = "^(([^\\<\\>\\(\\)\\[\\]\\.,;:\\s@\"]+(.[^\\<\\>\\(\\)\\[\\]\\.,;:\\s@\"]+)*)|(\".+\"))@(([^\\<\\>\\(\\)\\[\\]\\.,;:\\s@\"]+.)+[^\\<\\>\\(\\)\\[\\]\\.,;:\\s@\"]{2,})$";
  public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,15}$";
  public static final Integer MIN_EMAIL_LENGTH = 5;
  public static final Integer MAX_EMAIL_LENGTH = 50;
  public static final String ROLE_REGEX = "^[a-zA-Z0-9 _-]{3,15}$";
  public static final String REPORT_TITLE_REGEX = "^[a-zA-Z0-9 _-]{3,50}$";
  public static final String REPORT_DESCRIPTION_REGEX = "^[a-zA-Z0-9 _-]{3,200}$";
  public static final String REPORT_STATUS_REGEX = "^[a-zA-Z0-9 _-]{3,20}$";
  public static final String CATEGORY_REGEX = "^[a-zA-Z0-9 _-]{3,40}$";
  public static final String COMMENT_REGEX = "^[a-zA-Z0-9 _-]{1,200}$";
  public static final String QUIZ_REGEX = "^[a-zA-Z0-9 _-]{3,50}$";
  public static final String QUESTION_REGEX = "^[.]{3,1000}$";
  public static final String ANSWER_REGEX = "^[.]{3,4000}$";

  public static final Integer ANSWER_MAX_POINT_COUNT = 50;
  public static final Integer ANSWER_MIN_POINT_COUNT = -50;
  public static final Integer OPEN_QUESTION_ANSWER_POINTS = 0;

  public static String getApplicationName() {
    try {
      MavenXpp3Reader reader = new MavenXpp3Reader();
      Model model = reader.read(new FileReader("pom.xml"));
      return model.getName();
    } catch(XmlPullParserException | IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String getApplicationVersion() {
    try {
      MavenXpp3Reader reader = new MavenXpp3Reader();
      Model model = reader.read(new FileReader("pom.xml"));
      return model.getVersion();
    } catch(XmlPullParserException | IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  private Constants(){}
}
