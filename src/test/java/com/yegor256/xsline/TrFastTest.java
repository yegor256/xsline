/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XMLDocument;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrFast}.
 *
 * @since 0.12.0
 */
final class TrFastTest {

    @Test
    void simpleScenario() {
        new Xsline(
            new TrFast(
                new TrClasspath<>().with("add-brackets.xsl").back(),
                TrFastTest.class,
                1L
            )
        ).pass(new XMLDocument("<foo/>"));
    }

}
