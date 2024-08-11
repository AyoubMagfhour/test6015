package com.question.suggestion_question;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SuggestionQuestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuggestionQuestionApplication.class, args);
    }

}
