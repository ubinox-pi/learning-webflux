package com.webflux.webfluxlearning.sec05.mapper;

import com.webflux.webfluxlearning.sec05.dto.CustomerDto;
import com.webflux.webfluxlearning.sec05.entity.Customer;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec03.mapper
 * Created by: Ashish Kushwaha on 05-02-2026 19:47
 * File: CustomerMapper
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public class CustomerMapper {

    public static Customer toEntity(CustomerDto dto) {
        return Customer.builder()
                .name(dto.name())
                .email(dto.email())
                .build();
    }

    public static CustomerDto toDto(Customer entity) {
        return CustomerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
