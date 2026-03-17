package com.webflux.webfluxlearning.sec10;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec10
 * Created by: Ashish Kushwaha on 17-03-2026 14:07
 * File: Lec01HttpConnectionPoolingTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.webflux.webfluxlearning.sec10.dto.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.test.StepVerifier;


public class Lec01HttpConnectionPoolingTest extends AbstractWebClient {
    private final WebClient webClient = create(b -> {
        int poolSize = 9999;
        ConnectionProvider provider = ConnectionProvider.builder("ashish")
                .lifo()
                .maxConnections(poolSize)
                .pendingAcquireMaxCount(poolSize * 5)
                .build();
        HttpClient client = HttpClient.create(provider)
                .compress(true)
                .keepAlive(true);
        b.clientConnector(new ReactorClientHttpConnector(client));
    });

    @Test
    public void concurrentRequest() {
        int max = 9999;
        Flux.range(1, max)
                .flatMap(this::getProduct, max)
                .collectList()
                .as(StepVerifier::create)
                .assertNext(products -> Assertions.assertEquals(max, products.size()))
                .expectComplete()
                .verify();

    }

    private Mono<Product> getProduct(int id) {
        return this.webClient.get()
                .uri("/product/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);
    }
}
