package com.webflux.webfluxlearning.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec07
 * Created by: Ashish Kushwaha on 03-03-2026 18:20
 * File: AbstractWebClient
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

public abstract class AbstractWebClient {

    private static final Logger log = LoggerFactory.getLogger(AbstractWebClient.class);

    protected <T> Consumer<T> print() {
        return item -> log.info("received: {}", item);
    }

    protected WebClient create(Consumer<WebClient.Builder> consumer) {
        WebClient.Builder builder = WebClient.builder()
                .baseUrl("http://localhost:8080/demo02");

        consumer.accept(builder);

        return builder.build();
    }

    protected WebClient create() {
        return create(_ -> {
        });
    }


}
