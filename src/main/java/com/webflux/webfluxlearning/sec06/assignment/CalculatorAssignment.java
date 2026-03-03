package com.webflux.webfluxlearning.sec06.assignment;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec06.assignment
 * Created by: Ashish Kushwaha on 03-03-2026 14:24
 * File: CalculatorAssignment
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import java.util.function.BiFunction;

@Configuration
public class CalculatorAssignment {

    @Bean
    public RouterFunction<ServerResponse> calculator() {
        return RouterFunctions.route()
                .path("calculator", this::calculatorRoutes)
                .build();
    }

    private RouterFunction<ServerResponse> calculatorRoutes() {
        return RouterFunctions.route()
                .GET("/{a}/0", handle("b can not be zero."))
                .GET("/{a}/{b}", isOperation("+"), handle(Integer::sum))
                .GET("/{a}/{b}", isOperation("-"), handle((a, b) -> a - b))
                .GET("/{a}/{b}", isOperation("*"), handle((a, b) -> a * b))
                .GET("/{a}/{b}", isOperation("/"), handle((a, b) -> a / b))
                .GET("/{a}/{b}", handle("operation header should be one of +, -, *, /"))
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(h -> operation.equals(h.firstHeader("operation")));
    }

    private HandlerFunction<ServerResponse> handle(BiFunction<Integer, Integer, Integer> function) {
        return request -> {
            Integer a = Integer.parseInt(request.pathVariable("a"));
            Integer b = Integer.parseInt(request.pathVariable("b"));

            Integer result = function.apply(a, b);
            return ServerResponse.ok().bodyValue(result);
        };
    }

    private HandlerFunction<ServerResponse> handle(String message) {
        return _ -> ServerResponse.badRequest().bodyValue(message);
    }
}
