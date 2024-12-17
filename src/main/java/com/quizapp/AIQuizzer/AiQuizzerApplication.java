package com.quizapp.AIQuizzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.quizapp.AIQuizzer.Repository")
@EntityScan(basePackages = "com.quizapp.AIQuizzer.Entity")
public class AiQuizzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiQuizzerApplication.class, args);
	}

}
