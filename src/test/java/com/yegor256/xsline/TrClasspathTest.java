/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2022-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.yegor256.xsline;

import com.jcabi.xml.XMLDocument;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TrXSL}.
 *
 * @since 0.1.0
 */
final class TrClasspathTest {

    @Test
    void simpleScenario() {
        MatcherAssert.assertThat(
            new TrClasspath<>()
                .with("add-brackets.xsl")
                .back()
                .with(new StEndless(new StClasspath("void.xsl"))),
            Matchers.iterableWithSize(2)
        );
    }

    @Test
    void withCtor() {
        MatcherAssert.assertThat(
            new TrClasspath<>(
                "add-brackets.xsl",
                "add-id.xsl"
            ).back().with(new StEndless(new StClasspath("void.xsl"))),
            Matchers.iterableWithSize(3)
        );
    }

    @Test
    void emitsXslError() {
        MatcherAssert.assertThat(
            Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Xsline(new TrClasspath<>("emit-error.xsl").back()).pass(
                    new XMLDocument("<x>hello</x>")
                )
            ).getMessage(),
            Matchers.containsString("terminated by xsl:message at line 31 in emit-error.xsl")
        );
    }

    @Test
    void emitsXslErrorWhenDefault() {
        MatcherAssert.assertThat(
            Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Xsline(
                    new TrClasspath<>(
                        new TrDefault<>(),
                        "add-id.xsl",
                        "emit-error.xsl"
                    ).back()
                ).pass(new XMLDocument("<bar/>"))
            ).getMessage(),
            Matchers.containsString("by xsl:message at line 31 in emit-error.xsl")
        );
    }
}
