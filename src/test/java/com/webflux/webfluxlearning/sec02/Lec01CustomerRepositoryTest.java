package com.webflux.webfluxlearning.sec02;

import com.webflux.webfluxlearning.sec02.entity.Customer;
import com.webflux.webfluxlearning.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec02
 * Created by: Ashish Kushwaha on 29-01-2026 12:30
 * File: Lec01CustomerRepositoryTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

public class Lec01CustomerRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findAll() {
        this.customerRepository.findAll()
                .doOnNext(customer -> log.info("Customer: {}", customer))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete();
    }

    @Test
    public void findById() {
        this.customerRepository.findById(2)
                .doOnNext(customer -> log.info("Customer: {}", customer))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("mike", c.getName()))
                .expectNextCount(1)
                .expectComplete();
    }

    @Test
    public void findByName() {
        this.customerRepository.findByName("jake")
                .doOnNext(c -> log.info("Customer: {}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("jake@gamil.com", c.getEmail()))
                .expectNextCount(1)
                .expectComplete();
    }

    @Test
    public void findByEmail() {
        this.customerRepository.findByEmailEndingWith("ke@gamil.com")
                .doOnNext(c -> log.info("Customer: {}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("mike@gamil.com", c.getEmail()))
                .assertNext(c -> Assertions.assertEquals("jake@gamil.com", c.getEmail()))
                .expectNextCount(1)
                .expectComplete();
    }

    @Test
    public void insertAndDeleteCustomer() {
        // insert
        var customer = new Customer();
        customer.setName("Ashish");
        customer.setEmail("ashish@gmail.com");
        this.customerRepository.save(customer)
                .doOnNext(c -> log.info("Inserted Customer: {}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertNotNull(c.getId()))
                .expectComplete()
                .verify();

        // count
        this.customerRepository.count()
                .as(StepVerifier::create)
                .expectNext(11L)
                .expectComplete()
                .verify();

        this.customerRepository.deleteById(11)
                .then(this.customerRepository.count())
                .as(StepVerifier::create)
                .expectNext(10L)
                .expectComplete()
                .verify();
    }

    @Test
    public void updateCustomer() {
        this.customerRepository.findByName("ethan")
                .doOnNext(customer -> customer.setName("ashish"))
                .flatMap(customer -> customerRepository.save(customer))
                .doOnNext(c -> log.info("Updated Customer: {}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertNotNull("ashish", c.getName()))
                .expectComplete()
                .verify();
    }
}
