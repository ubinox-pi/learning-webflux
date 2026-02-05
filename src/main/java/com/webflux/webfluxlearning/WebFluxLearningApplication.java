package com.webflux.webfluxlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.webflux.webfluxlearning.${sec}")
@EnableR2dbcRepositories(basePackages = "com.webflux.webfluxlearning.${sec}")
public class WebFluxLearningApplication {

    static void main(String[] args) {
        SpringApplication.run(WebFluxLearningApplication.class, args);
    }

}
