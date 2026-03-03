package com.webflux.webfluxlearning.sec06.config;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec04.controllerAdvice
 * Created by: Ashish Kushwaha on 07-02-2026 15:00
 * File: ApplicationExceptionHandler
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.webflux.webfluxlearning.sec06.exceptions.CustomerNotFoundException;
import com.webflux.webfluxlearning.sec06.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

@Service
public class ApplicationExceptionHandler {

    public Mono<ServerResponse> handleCustomerNotFoundException(CustomerNotFoundException ex, ServerRequest serverRequest) {
        return handleCustomerNotFoundException(HttpStatus.NOT_FOUND, ex, serverRequest, problemDetail -> {
            problemDetail.setTitle("Invalid Input");
            problemDetail.setType(URI.create("http://localhost:8081/problems/invalid-input"));
        });
    }

    public Mono<ServerResponse> handleCustomerNotFoundException(InvalidInputException ex, ServerRequest serverRequest) {
        return handleCustomerNotFoundException(HttpStatus.BAD_REQUEST, ex, serverRequest, problemDetail -> {
            problemDetail.setTitle("Invalid Input");
            problemDetail.setType(URI.create("http://localhost:8081/problems/invalid-input"));
        });
    }

    private Mono<ServerResponse> handleCustomerNotFoundException(
            HttpStatus status,
            Exception exception,
            ServerRequest serverRequest,
            Consumer<ProblemDetail> consumer) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setInstance(URI.create(serverRequest.path()));
        consumer.accept(problemDetail);
        return ServerResponse.status(status).bodyValue(problemDetail);
    }

}
