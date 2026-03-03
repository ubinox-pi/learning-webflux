package com.webflux.webfluxlearning.sec06;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec06
 * Created by: Ashish Kushwaha on 03-03-2026 14:48
 * File: CalculatorAssignmentTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec06")
public class CalculatorAssignmentTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void calculator() {

        // success
        validate(20, 10, "+", 200, "30");
        validate(20, 10, "-", 200, "10");
        validate(20, 10, "*", 200, "200");
        validate(20, 10, "/", 200, "2");

        // bad requests
        validate(20, 0, "/", 400, "b can not be zero.");
        validate(20, 10, "@", 400, "operation header should be one of +, -, *, /");
        validate(20, 10, null, 400, "operation header should be one of +, -, *, /");
    }

    private void validate(int a, int b, String operation, int statusCode, String expectedBody) {
        this.webTestClient.get()
                .uri("/calculator/{a}/{b}", a, b)
                .headers(h -> {
                    if (Objects.nonNull(operation))
                        h.add("operation", operation);
                })
                .exchange()
                .expectStatus().isEqualTo(statusCode)
                .expectBody(String.class)
                .value(s -> {
                    Assertions.assertNotNull(s);
                    Assertions.assertEquals(expectedBody, s);
                });

    }
}
