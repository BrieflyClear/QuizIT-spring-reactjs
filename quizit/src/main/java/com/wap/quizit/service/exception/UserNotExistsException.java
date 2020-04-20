package com.wap.quizit.service.exception;

public class UserNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException(String email) {
        super("The user with email [" + email + "] does not exist!");
    }

    public UserNotExistsException(Long id) {
        super("The user with ID [" + id + "] does not exist!");
    }
}
