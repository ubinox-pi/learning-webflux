package com.webflux.webfluxlearning.sec09.service;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec08.service
 * Created by: Ashish Kushwaha on 16-03-2026 10:49
 * File: ProductService
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.webflux.webfluxlearning.sec09.dto.ProductDto;
import com.webflux.webfluxlearning.sec09.dtoMapper.ProductDtoMapper;
import com.webflux.webfluxlearning.sec09.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Sinks.Many<ProductDto> sink;

    public Mono<ProductDto> saveProduct(Mono<ProductDto> mono) {
        return mono
                .map(ProductDtoMapper::toEntity)
                .flatMap(this.productRepository::save)
                .map(ProductDtoMapper::toDto)
                .doOnNext(this.sink::tryEmitNext);
    }

    public Flux<ProductDto> productStream() {
        return this.sink.asFlux();
    }
}
