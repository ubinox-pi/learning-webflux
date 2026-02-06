package com.webflux.webfluxlearning.sec03.service;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec03.service
 * Created by: Ashish Kushwaha on 05-02-2026 19:54
 * File: CustomerService
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import com.webflux.webfluxlearning.sec03.dto.CustomerDto;
import com.webflux.webfluxlearning.sec03.mapper.CustomerMapper;
import com.webflux.webfluxlearning.sec03.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Flux<CustomerDto> getAllCustomers() {
        return this.customerRepository.findAll()
                .map(CustomerMapper::toDto);
    }

    public Flux<CustomerDto> getAllCustomers(Integer page, Integer size) {
        return this.customerRepository.findBy(PageRequest.of(page - 1, size))
                .map(CustomerMapper::toDto);
    }

    public Mono<CustomerDto> getCustomerById(Integer id) {
        return this.customerRepository.findById(id)
                .map(CustomerMapper::toDto);
    }

    public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> mono) {
        return mono
                .map(CustomerMapper::toEntity)
                .flatMap(this.customerRepository::save)
                .map(CustomerMapper::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Integer id, Mono<CustomerDto> mono) {
        return this.customerRepository.findById(id)
                .flatMap(entity -> mono)
                .map(CustomerMapper::toEntity)
                .doOnNext(c -> c.setId(id))
                .flatMap(this.customerRepository::save)
                .map(CustomerMapper::toDto);
    }

    public Mono<Boolean> deleteCustomerById(Integer id) {
        return this.customerRepository.deleteCustomerById(id);
    }
}
