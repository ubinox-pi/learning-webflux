package com.webflux.webfluxlearning.sec07;

import com.webflux.webfluxlearning.sec07.dto.CalculatorResponse;
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
 * Created by: Ashish Kushwaha on 15-03-2026 22:43
 * File: Lec06QueryParamsTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class Lec06QueryParamsTest extends AbstractWebClient {

    private final WebClient client = create();

    @Test
    public void uriBuilderVariables() {

        String path = "/lec06/calculator";
        String query = "first={first}&second={second}&operation={operation}";

        this.client.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(1, 2, "+"))
                .retrieve()
                .bodyToFlux(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }

    @Test
    public void uriBuilderMap() {

        String path = "/lec06/calculator";
        String query = "first={first}&second={second}&operation={operation}";

        Map<String, Object> map = Map.of(
                "first", 10,
                "second", 20,
                "operation", "+"
        );

        this.client.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(map))
                .retrieve()
                .bodyToFlux(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }
}
