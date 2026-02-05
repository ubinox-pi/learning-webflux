package com.webflux.webfluxlearning.sec02.repository;

import com.webflux.webfluxlearning.sec02.entity.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec02.repository
 * Created by: Ashish Kushwaha on 29-01-2026 12:20
 * File: CustomerRepository
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    @Query("SELECT * FROM customer WHERE name = :name")
    Flux<Customer> findByName(String name);

    @Query("SELECT * FROM customer WHERE email LIKE '%' || :email")
    Flux<Customer> findByEmailEndingWith(String email);

}
