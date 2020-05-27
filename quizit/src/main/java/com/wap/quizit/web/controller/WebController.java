package com.wap.quizit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @GetMapping({"/", "quizzes"})
    public String showWelcome() {
        return "index";
    }

    @GetMapping({"/add"})
    public String showAddForm() {
        return "addQuiz";
    }

    @GetMapping({"/login"})
    public String showLoginForm() {
        return "login";
    }

    @GetMapping({"/register"})
    public String showRegisterForm() {
        return "register";
    }

    @GetMapping({"/myQuizzes"})
    public String showMyQuizzes() {
        return "myQuizzes";
    }

    @GetMapping({"/quizzes/{id}"})
    public String showQuizPage(@PathVariable String id) {
        return "quiz";
    }

    @GetMapping({"/attempts/{id}"})
    public String showAttemptPage(@PathVariable String id) {
        return "attemptPage";
    }

}
