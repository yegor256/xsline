/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XSL;
import com.jcabi.xml.XSLDocument;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrXSL}.
 *
 * @since 0.6.0
 * @checkstyle AbbreviationAsWordInNameCheck (500 lines)
 */
final class TrXSLTest {

    @Test
    void testPipe() throws IOException {
        final XSL xsl = new XSLDocument(this.getClass().getResource("void.xsl"));
        final Train<Shift> train = new TrXSL<>()
            .with(xsl)
            .back();
        MatcherAssert.assertThat(train, Matchers.iterableWithSize(1));
    }

    @Test
    void shouldThrow() throws IOException {
        final XSL xsl = new XSLDocument(this.getClass().getResource("add-brackets.xsl"));
        Assertions.assertThrows(
            UnsupportedOperationException.class,
            () -> new TrXSL<>()
                .with(xsl)
                .iterator()
        );
    }

}
