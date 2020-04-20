package com.wap.quizit.service.exception;

public class RoleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RoleNotFoundException() {
        super("The user role is not present in the database!");
    }
}
