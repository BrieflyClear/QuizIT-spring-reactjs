package com.wap.quizit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class QuizItApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizItApplication.class, args);
	}

}
