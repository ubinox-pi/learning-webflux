package com.webflux.webfluxlearning.sec05.filter;

import org.jspecify.annotations.NullMarked;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec05.filter
 * Created by: Ashish Kushwaha on 08-02-2026 19:32
 * File: AuthenticationFilter
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

@Order(1)
@Service
public class AuthenticationFilter implements WebFilter {

    private static final Map<String, Category> TOKEN_CATEGORY_MAP = Map.of(
            "secret123", Category.STANDARD,
            "secret456", Category.PRIME
    );

    @NullMarked
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("auth-token");
        if (Objects.nonNull(token) && TOKEN_CATEGORY_MAP.containsKey(token)) {
            exchange.getAttributes().put("category", TOKEN_CATEGORY_MAP.get(token));
            return chain.filter(exchange);
        }

        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
    }
}
