package com.webflux.webfluxlearning.sec05.filter;

import org.jspecify.annotations.NullMarked;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec05.filter
 * Created by: Ashish Kushwaha on 08-02-2026 19:40
 * File: AuthorizationWebFilter
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

@Order(2)
@Service
public class AuthorizationWebFilter implements WebFilter {

    @NullMarked
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Category category = exchange.getAttributeOrDefault("category", Category.STANDARD);
        return switch (category) {
            case PRIME -> prime(exchange, chain);
            case STANDARD -> standard(exchange, chain);
        };
    }

    private Mono<Void> prime(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange);
    }

    private Mono<Void> standard(ServerWebExchange exchange, WebFilterChain chain) {
        boolean isGet = HttpMethod.GET.equals(exchange.getRequest().getMethod());
        if (isGet) {
            return chain.filter(exchange);
        }
        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
    }
}
