package com.webflux.webfluxlearning.sec01.controler;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec01.controler
 * Created by: Ashish Kushwaha on 23-01-2026 18:22
 * File: TraditionalWebController
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.webflux.webfluxlearning.sec01.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/traditional")
public class TraditionalWebController {

    private static final Logger log = LoggerFactory.getLogger(TraditionalWebController.class);
    private final RestClient restClient = RestClient.create().mutate()
            .requestFactory(new JdkClientHttpRequestFactory())
            .baseUrl("http://localhost:8080").build();

    @GetMapping("/products")
    public List<Product> getProducts() {
        var list = this.restClient
                .get()
                .uri("/demo01/products/notorious")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });
        log.info("Received response: {}", list);

        return list;
    }
    @GetMapping("/products2")
    public Flux<Product> getProducts2() {
        var list = this.restClient
                .get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });
        log.info("Received response...: {}", list);

        return Flux.fromIterable(Objects.requireNonNull(list));
    }
}
