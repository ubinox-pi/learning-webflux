package com.webflux.webfluxlearning.sec08.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec08.entity
 * Created by: Ashish Kushwaha on 16-03-2026 10:42
 * File: Product
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
@ToString
public class Product {

    @Id
    private Integer id;
    private String description;
    private Integer price;
}
