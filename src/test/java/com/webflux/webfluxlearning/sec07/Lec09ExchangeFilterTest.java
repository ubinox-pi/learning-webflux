package com.webflux.webfluxlearning.sec07;

import com.webflux.webfluxlearning.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.UUID;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec07
 * Created by: Ashish Kushwaha on 15-03-2026 23:19
 * File: Lec09ExchangeFilterTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

public class Lec09ExchangeFilterTest extends AbstractWebClient {

    private static final Logger log = LoggerFactory.getLogger(Lec09ExchangeFilterTest.class);
    private final WebClient client = create(b -> b.filter(tokenGenerator())
            .filter(requestLogger()));

    @Test
    public void exchangeFilter() {

        for (int i = 0; i < 5; i++) {
            this.client.get()
                    .uri("/lec09/product/{id}", 1)
                    .attribute("enable-logging", i % 2 == 0)
                    .retrieve()
                    .bodyToFlux(Product.class)
                    .doOnNext(print())
                    .then()
                    .as(StepVerifier::create)
                    .expectComplete()
                    .verify();
        }

    }

    private ExchangeFilterFunction tokenGenerator() {
        return (request, next) -> {
            String token = UUID.randomUUID().toString().replace("-", "");
            log.info("Generated token: {}", token);
            ClientRequest newRequest = ClientRequest.from(request).headers(h -> h.setBearerAuth(token)).build();
            return next.exchange(newRequest);
        };
    }

    private ExchangeFilterFunction requestLogger() {
        return (request, next) -> {
            boolean isEnabled = (boolean) request.attributes().getOrDefault("enable-logging", false);
            if (isEnabled)
                log.info("Request: {} {}", request.method(), request.url());
            return next.exchange(request);
        };
    }
}
