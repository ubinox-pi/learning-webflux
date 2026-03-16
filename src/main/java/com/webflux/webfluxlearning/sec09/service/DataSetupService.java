package com.webflux.webfluxlearning.sec09.service;

import com.webflux.webfluxlearning.sec09.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec09.service
 * Created by: Ashish Kushwaha on 16-03-2026 15:19
 * File: DataSetupService
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        Flux.range(1, 1000)
                .delayElements(Duration.ofMillis(1000))
                .map(i -> ProductDto.builder().id(null)
                        .description("Product-" + i)
                        .price(ThreadLocalRandom.current().nextInt(1, 1000))
                        .build())
                .flatMap(productDto -> this.productService.saveProduct(Mono.just(productDto)))
                .subscribe();
    }
}
