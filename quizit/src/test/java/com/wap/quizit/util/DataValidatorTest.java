package com.wap.quizit.util;

import com.wap.quizit.service.exception.EntityFieldValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataValidatorTest {

  @Test
  void validateUsername() {
    String username = "LoremIpsum";
    String username2 = "Lorem Ipsum";
    String username3 = "Foo";
    String username4 = "Foo.bar";
    String username5 = "Foo500";
    String username6 = "";
    String username7 = "----------------";
    assertDoesNotThrow(() -> DataValidator.validateUsername(username));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validateUsername(username2));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validateUsername(username3));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validateUsername(username4));
    assertDoesNotThrow(() -> DataValidator.validateUsername(username5));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validateUsername(username6));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validateUsername(username7));
  }

  @Test
  void validatePassword() {
    String password = "LoremIpsum1";
    String password2 = "Lorem-Ipsum";
    String password3 = "Foo";
    String password4 = "Foo.bar";
    String password5 = "Foo500";
    String password6 = "";
    String password7 = "----------------";
    assertDoesNotThrow(() -> DataValidator.validatePassword(password));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validatePassword(password2));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validatePassword(password3));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validatePassword(password4));
    assertDoesNotThrow(() -> DataValidator.validatePassword(password5));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validatePassword(password6));
    assertThrows(EntityFieldValidationException.class, () -> DataValidator.validatePassword(password7));
  }
}