package com.webflux.webfluxlearning.sec02;

/*
 * Copyright (c) 2026 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See the LICENSE file in the project root for full license information.
 *
 * Project: WebFluxLearning
 * Package: com.webflux.webfluxlearning.sec02
 * Created by: Ashish Kushwaha on 29-01-2026 12:26
 * File: AbstractTest
 *
 * This source code is intended for educational and non-commercial purposes only.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *   - Attribution must be given to the original author.
 *   - The code must be shared under the same license.
 *   - Commercial use is strictly prohibited.
 *
 */

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties =
        """
                spring.r2dbc.properties.quote-identifiers=false
                logging.level.org.springframework.r2dbc=DEBUG
                sec=sec02
                """)
public abstract class AbstractTest {

}
