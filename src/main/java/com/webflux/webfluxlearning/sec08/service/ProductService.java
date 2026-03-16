package com.webflux.webfluxlearning.sec08.service;

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

import com.webflux.webfluxlearning.sec08.dto.ProductDto;
import com.webflux.webfluxlearning.sec08.dtoMapper.ProductDtoMapper;
import com.webflux.webfluxlearning.sec08.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<ProductDto> saveProduct(Flux<ProductDto> flux) {
        return flux.map(ProductDtoMapper::toEntity)
                .doOnNext(product -> product.setId(null))
                .as(this.productRepository::saveAll)
                .map(ProductDtoMapper::toDto);
    }

    public Mono<Long> getProductCount() {
        return this.productRepository.count();
    }

    public Flux<ProductDto> allProducts() {
        return this.productRepository.findAll()
                .map(ProductDtoMapper::toDto);
    }
}
