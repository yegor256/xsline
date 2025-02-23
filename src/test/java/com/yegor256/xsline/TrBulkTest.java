/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import java.util.Arrays;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrBulk}.
 *
 * @since 0.4.0
 */
final class TrBulkTest {

    @Test
    void simpleScenario() {
        MatcherAssert.assertThat(
            new TrBulk<>(new TrClasspath<>(new TrFast()))
                .with(Arrays.asList("add-brackets.xsl", "void.xsl"))
                .back()
                .back()
                .with(new StEndless(new StClasspath("add-id.xsl"))),
            Matchers.iterableWithSize(3)
        );
    }

    @Test
    void allInCtor() {
        MatcherAssert.assertThat(
            new TrWith(
                new TrBulk<>(
                    new TrClasspath<>(),
                    "add-brackets.xsl",
                    "void.xsl"
                ).back().back(),
                new StEndless(new StClasspath("add-id.xsl"))
            ),
            Matchers.iterableWithSize(3)
        );
    }

}
