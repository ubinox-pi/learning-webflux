package com.webflux.webfluxlearning.sec07;

import com.webflux.webfluxlearning.sec07.dto.CalculatorResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec07
 * Created by: Ashish Kushwaha on 15-03-2026 16:08
 * File: Lec05ErrorResponseTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class Lec05ErrorResponseTest extends AbstractWebClient {
    private static final Logger log = LoggerFactory.getLogger(Lec05ErrorResponseTest.class);
    private final WebClient client = create();

    @Test
    public void handlingError() {

        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 0)
                .header("operation", "@")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
//                .onErrorReturn(new CalculatorResponse(0, 0, null, 0))
                .doOnError(WebClientResponseException.class, ex -> log.info("Error response: {}", ex.getResponseBodyAs(ProblemDetail.class)))
                .onErrorReturn(WebClientResponseException.class, new CalculatorResponse(0, 0, null, 0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }

    @Test
    public void exchange() {

        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 0)
                .header("operation", "@")
                .exchangeToMono(this::decode)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }

    private Mono<CalculatorResponse> decode(ClientResponse clientResponse) {
//        clientResponse.cookies();
//        clientResponse.headers();
        log.info("Status code: {}", clientResponse.statusCode());
        if (clientResponse.statusCode().is4xxClientError()) {
            return clientResponse.bodyToMono(ProblemDetail.class)
                    .doOnNext(pd -> log.info("Problem detail: {}", pd))
                    .then(Mono.empty());
        }

        return clientResponse.bodyToMono(CalculatorResponse.class);

    }
}
