package com.webflux.webfluxlearning.sec09.dto;

import java.util.UUID;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec08.dto
 * Created by: Ashish Kushwaha on 16-03-2026 10:54
 * File: UploadResponse
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */
public record UploadResponse(
        UUID confirmationId,
        Long productsCount
) {
}
