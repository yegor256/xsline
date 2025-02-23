/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrDefault}.
 *
 * @since 0.4.0
 */
final class TrDefaultTest {

    @Test
    void startWithMany() {
        MatcherAssert.assertThat(
            new TrFast(
                new TrDefault<>(
                    new StClasspath("add-id.xsl"),
                    new StClasspath("add-brackets.xsl")
                )
            ),
            Matchers.iterableWithSize(2)
        );
    }

}
