package com.webflux.webfluxlearning.sec06.config;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec06.config
 * Created by: Ashish Kushwaha on 09-02-2026 16:14
 * File: RouterConfig
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
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final CustomerRequestHandler customerRequestHandler;
    private final ApplicationExceptionHandler applicationExceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> customerRoutes1() {
        return RouterFunctions.route()
                .path("customers", this::customerRoutes2)
                .GET("/customers", customerRequestHandler::allCustomer)
                .GET("/customers/paginated", customerRequestHandler::paginatedCustomer)
                .GET("/customers/{id}", customerRequestHandler::getCustomerById)
                .onError(CustomerNotFoundException.class, this.applicationExceptionHandler::handleCustomerNotFoundException)
                .onError(InvalidInputException.class, this.applicationExceptionHandler::handleCustomerNotFoundException)
                .build();
    }

    public RouterFunction<ServerResponse> customerRoutes2() {
        return RouterFunctions.route()
                .POST("/customers", customerRequestHandler::saveCustomer)
                .PUT("/customers/{id}", customerRequestHandler::updateCustomer)
                .DELETE("/customers/{id}", customerRequestHandler::deleteCustomerById)
                .onError(CustomerNotFoundException.class, this.applicationExceptionHandler::handleCustomerNotFoundException)
                .onError(InvalidInputException.class, this.applicationExceptionHandler::handleCustomerNotFoundException)
                .build();
    }
}
