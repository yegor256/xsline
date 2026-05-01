/*
 * SPDX-FileCopyrightText: Copyright (c) 2022-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.matchers.XhtmlMatchers;
import com.jcabi.xml.XMLDocument;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StClasspath}.
 * @since 0.6.0
 */
final class StClasspathTest {

    @Test
    void throwsIfResourceIsAbsent() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new StClasspath("not-found")
        );
    }

    @Test
    void addsParam() {
        MatcherAssert.assertThat(
            new StClasspath("add-param.xsl", "param hello, world!").apply(
                0, new XMLDocument("<x>hello</x>")
            ),
            XhtmlMatchers.hasXPaths("/x[@param='hello, world!']")
        );
    }

    @Test
    void emitsXslError() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new StClasspath("emit-error.xsl").apply(
                0, new XMLDocument("<x>hello</x>")
            )
        );
    }
}
