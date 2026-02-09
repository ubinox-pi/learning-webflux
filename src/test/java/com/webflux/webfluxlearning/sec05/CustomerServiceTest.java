package com.webflux.webfluxlearning.sec05;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec05
 * Created by: Ashish Kushwaha on 09-02-2026 10:54
 * File: CustomerServiceTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.webflux.webfluxlearning.sec03.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec05")
public class CustomerServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void unauthorised() {

        // no token scenario
        this.webTestClient
                .get()
                .uri("/customers")
                .exchange()
                .expectStatus().isUnauthorized();


        this.validateGet("secret", HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void standardCategory() {
        this.validateGet("secret123", HttpStatus.OK);
        this.validatePost("secret123", HttpStatus.FORBIDDEN);
    }

    @Test
    public void primeCategory() {
        this.validateGet("secret456", HttpStatus.OK);
        this.validatePost("secret456", HttpStatus.OK);
    }


    private void validateGet(String token, HttpStatus expectedStatus) {
        this.webTestClient
                .get()
                .uri("/customers")
                .header("auth-token", token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    private void validatePost(String token, HttpStatus expectedStatus) {
        CustomerDto dto = CustomerDto.builder()
                .name("Ashish")
                .email("ashish@gamil.com").build();
        this.webTestClient
                .post()
                .uri("/customers")
                .bodyValue(dto)
                .header("auth-token", token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

}
