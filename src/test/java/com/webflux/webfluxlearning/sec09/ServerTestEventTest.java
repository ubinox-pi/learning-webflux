package com.webflux.webfluxlearning.sec09;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec09
 * Created by: Ashish Kushwaha on 16-03-2026 15:29
 * File: ServerTestEventTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.webflux.webfluxlearning.sec09.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest(properties = "sec=sec09")
@AutoConfigureWebTestClient
public class ServerTestEventTest {

    private static final Logger log = LoggerFactory.getLogger(ServerTestEventTest.class);
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void serverSentEvents() {
        this.webTestClient.get()
                .uri("/products/stream/1200")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(Product.class)
                .getResponseBody()
                .take(3)
                .doOnNext(sto -> log.info("Received: {}", sto))
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list -> {
                    Assertions.assertEquals(3, list.size());
                    Assertions.assertTrue(list.stream().allMatch(p -> p.getPrice() <= 1200));
                })
                .expectComplete()
                .verify();
    }

}
