/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrXSL}.
 * @since 0.6.0
 */
// @checkstyle AbbreviationAsWordInNameCheck (500 lines)
final class TrXSLTest {

    @Test
    void pipesShifts() throws IOException {
        MatcherAssert.assertThat(
            "Train must contain exactly one shift",
            new TrXSL<>()
                .with(new XSLDocument(this.getClass().getResource("void.xsl")))
                .back(),
            Matchers.iterableWithSize(1)
        );
    }

    @Test
    void throwsOnInvalid() throws IOException {
        Assertions.assertThrows(
            UnsupportedOperationException.class,
            () -> new TrXSL<>()
                .with(new XSLDocument(this.getClass().getResource("add-brackets.xsl")))
                .iterator()
        );
    }
}
