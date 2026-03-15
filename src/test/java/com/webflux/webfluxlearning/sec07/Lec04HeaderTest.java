package com.webflux.webfluxlearning.sec07;

import com.webflux.webfluxlearning.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec07
 * Created by: Ashish Kushwaha on 15-03-2026 14:48
 * File: Lec04HeaderTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class Lec04HeaderTest extends AbstractWebClient {

    private final WebClient client = create(header -> header.defaultHeader("caller-id", "webflux-learning"));

    @Test
    public void defaultHeader() {

        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }

    @Test
    public void overrideHeader() {

        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .header("caller-id", "webflux-learning-override")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }

    @Test
    public void headersWithMap() {

        Map<String, String> headers = Map.of(
                "caller-id", "webflux-learning-override",
                "some-key", "some-value"
        );

        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .headers(h -> h.setAll(headers))
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }
}
