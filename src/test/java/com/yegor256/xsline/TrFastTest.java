/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrFast}.
 *
 * @since 0.12.0
 */
final class TrFastTest {

    @Test
    void simpleScenario() {
        MatcherAssert.assertThat(
            "Fast train applies transformation correctly",
            new Xsline(
                new TrFast(
                    new TrClasspath<>().with("add-brackets.xsl").back(),
                    TrFastTest.class,
                    1L
                )
            ).pass(new XMLDocument("<foo/>")),
            XhtmlMatchers.hasXPaths("/foo")
        );
    }

}
