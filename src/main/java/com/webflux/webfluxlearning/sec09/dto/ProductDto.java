package com.webflux.webfluxlearning.sec09.dto;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec08.dto
 * Created by: Ashish Kushwaha on 16-03-2026 10:44
 * File: ProductDto
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
@Builder
@JsonDeserialize(builder = ProductDto.ProductDtoBuilder.class)
@JsonSerialize(as = ProductDto.class)
public class ProductDto {

    private Integer id;
    private String description;
    private Integer price;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductDtoBuilder {
    }
}
